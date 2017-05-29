package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

import java.util.List;

public class CustomerDataImportWriter implements ItemWriter<Customer> {

    private CustomerRepository customerRepository;

    private UserService userService;
    public SimpleMailMessage template;
    public EmailServiceImpl emailService;


    public CustomerDataImportWriter(CustomerRepository customerRepository, UserService userService, SimpleMailMessage template, EmailServiceImpl emailService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.template = template;
        this.emailService = emailService;
    }

    @Override
    public void write(List<? extends Customer> vendors) throws Exception {
        for (Customer customer : vendors) {
            customerRepository.save(customer);
            if (userService.findByUsername(customer.getCustomerId()) == null) {
                User user = new User();
                user.setUsername(customer.getCustomerId());
                user.setPassword("welcome123");
                user.setPasswordConfirm("welcome123");
                user.setEmail(customer.getEmail());
                user.setUserStatus(UserStatusEnum.NEW);
                userService.save(user);

                if (!StringUtils.isEmpty(user.getEmail())) {
                    String text = String.format(template.getText(), "http://localhost:8080/login", user.getUsername(), "welcome123");
                    emailService.sendSimpleMessage(user.getEmail(), "Welcome to Customer Management Portal", text);
                }
            }
        }
    }
}
