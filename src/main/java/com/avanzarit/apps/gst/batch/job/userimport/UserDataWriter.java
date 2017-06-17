package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.email.MAIL_SENDER;
import com.avanzarit.apps.gst.email.VendorMailProperties;
import com.avanzarit.apps.gst.properties.AppProperties;
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

import java.util.List;

@Component
public class
UserDataWriter implements ItemWriter<User>, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataWriter.class);
    private UserService userService;
    private RoleRepository roleRepository;
    private EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;
    private UserProperties userProperties;
    private BatchLog logger;

    @Autowired
    VendorMailProperties vendorMailProperties;
    private ApplicationContext applicationContext;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
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
    public void write(List<? extends User> users) throws Exception {

        for (User user : users) {
            user.setPassword(defaultPassword);
            user.setUserStatus(UserStatusEnum.NEW);
            userService.save(user);
            SimpleMailMessage mailTemplate = (SimpleMailMessage) applicationContext.getBean("updatePasswordMessage", vendorMailProperties.isFromMailIdDifferent());
            if (userProperties.isSendEmailOnCreate()) {
                String text = String.format(mailTemplate.getText(), contextURL, user.getUsername(), defaultPassword, vendorMailProperties.getFromMailId());
                try {
                    emailService.sendSimpleMessage(user.getEmail(), "Welcome to PCBL GST Portal", text, MAIL_SENDER.VENDOR);
                } catch (Exception e) {
                    logger.log("WARNING: E-mail send Error: " + e.getMessage());
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
