package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.properties.AppProperties;
import com.avanzarit.apps.gst.storage.StorageProperties;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDataWriter implements ItemWriter<User> {

    private UserService userService;
    private RoleRepository roleRepository;
    private SimpleMailMessage template;
    private EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setTemplate(SimpleMailMessage updatePasswordMessage) {
        this.template = updatePasswordMessage;
    }

    @Autowired
    public void setEmailService(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setDefaultPassword(UserProperties properties) {
        this.defaultPassword = properties.getDefaultPassword();
    }

    @Autowired
    public void setContextURL(AppProperties properties) {
        this.contextURL = properties.getContextURL();

    }

    @Override
    public void write(List<? extends User> users) throws Exception {

        for (User user : users) {
            user.setPassword(defaultPassword);
            user.setUserStatus(UserStatusEnum.NEW);
            userService.save(user);

            String text = String.format(template.getText(), contextURL, user.getUsername(), defaultPassword);
            emailService.sendSimpleMessage(user.getEmail(), "Welcome to Vendor Management Portal", text);
        }
    }
}
