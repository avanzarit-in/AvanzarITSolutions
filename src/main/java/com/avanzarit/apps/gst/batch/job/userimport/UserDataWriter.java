package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.db.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.db.repository.RoleRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class
UserDataWriter implements ItemWriter<DbUser>, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataWriter.class);
    private UserService<DbUser> userService;
    private RoleRepository roleRepository;
    private EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;
    private UserProperties userProperties;
    private BatchLog logger;

    @Autowired
    VendorMailProperties vendorMailProperties;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ApplicationContext applicationContext;

    @Autowired
    public void setUserService(UserService<DbUser> userService) {
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
    public void write(List<? extends DbUser> users) throws Exception {

        for (DbUser dbUser : users) {
            dbUser.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
            dbUser.setUserStatus(UserStatusEnum.NEW);
            userService.save(dbUser);
            SimpleMailMessage mailTemplate = (SimpleMailMessage) applicationContext.getBean("updatePasswordMessage", vendorMailProperties.isFromMailIdDifferent(), MAIL_SENDER.VENDOR);
            if (userProperties.isSendEmailOnCreate()) {
                String text = String.format(mailTemplate.getText(), contextURL, dbUser.getUsername(), defaultPassword, vendorMailProperties.getFromMailId());
                String subject = vendorMailProperties.getUpdatePasswordSubject();
                try {
                    emailService.sendSimpleMessage(dbUser.getEmail(), subject, text, MAIL_SENDER.VENDOR);
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
