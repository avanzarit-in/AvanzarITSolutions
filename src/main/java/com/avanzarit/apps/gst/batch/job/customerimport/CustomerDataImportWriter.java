package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.email.CustomerMailProperties;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.email.MAIL_SENDER;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.properties.AppProperties;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomerDataImportWriter implements ItemWriter<Customer>, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDataImportWriter.class);
    private UserService userService;
    private RoleRepository roleRepository;
    private CustomerRepository customerRepository;
    private EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;
    private BatchLog logger;

    @Autowired
    CustomerMailProperties customerMailProperties;
    private ApplicationContext applicationContext;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEmailService(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setDefaultPassword(UserProperties properties) {
        this.defaultPassword = properties.getDefaultPassword();
    }

    @Autowired
    public void setContextURL(AppProperties properties) {
        this.contextURL = properties.getContextURL();

    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        logger = (BatchLog) stepExecution.getJobExecution().getExecutionContext().get("log");
    }

    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        for (Customer customer : customers) {

            customerRepository.save(customer);

            if (userService.findByUsername(customer.getCustomerId()) == null) {
                Role vendorRole = roleRepository.findByName("CUSTOMER");
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(vendorRole);
                User user = new User();
                user.setUsername(customer.getCustomerId());
                user.setPassword(defaultPassword);
                user.setEmail(customer.getEmail());
                user.setTelephone(customer.getTelephoneNumberExtn());
                user.setMobile(customer.getMobileNo());
                user.setUserStatus(UserStatusEnum.NEW);
                user.setRoles(roleSet);
                userService.save(user);
                SimpleMailMessage mailTemplate = (SimpleMailMessage) applicationContext.getBean("updatePasswordMessage", customerMailProperties.isFromMailIdDifferent(), MAIL_SENDER.CUSTOMER);
                if (!StringUtils.isEmpty(user.getEmail())) {
                    String text = String.format(mailTemplate.getText(), contextURL, user.getUsername(), defaultPassword, customerMailProperties.getFromMailId());
                    String subject = customerMailProperties.getUpdatePasswordSubject();
                    try {
                        emailService.sendSimpleMessage(user.getEmail(), subject, text, MAIL_SENDER.CUSTOMER);
                    } catch (Exception e) {
                        logger.log("WARNING: E-mail send Error: " + e.getMessage());
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
