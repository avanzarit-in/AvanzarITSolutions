package com.avanzarit.apps.gst;

import com.avanzarit.apps.gst.email.CustomerMailProperties;
import com.avanzarit.apps.gst.email.VendorMailProperties;
import com.avanzarit.apps.gst.interceptor.ThymeleafLayoutInterceptor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by SPADHI on 5/3/2017.
 */
@SpringBootApplication
@EnableBatchProcessing
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Qualifier("dataSource")
    @Autowired
    DataSource datasource;

    @Autowired
    CustomerMailProperties customerMailProperties;

    @Autowired
    VendorMailProperties vendorMailProperties;

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThymeleafLayoutInterceptor());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(datasource);
    }


    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
                container.addErrorPages(error404Page, error500Page);
            }
        };
    }


    @Bean(name = "javaCustomerMailSender")
    public JavaMailSender getJavaCustomerMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(customerMailProperties.getHost());
        mailSender.setPort(customerMailProperties.getPort());

        mailSender.setUsername(customerMailProperties.getUsername());
        mailSender.setPassword(customerMailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put(customerMailProperties.getAuth(), "true");
        props.put(customerMailProperties.getStarttls(), "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


    @Bean(name = "javaVendorMailSender")
    public JavaMailSender getJavaVendorMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(vendorMailProperties.getHost());
        mailSender.setPort(vendorMailProperties.getPort());

        mailSender.setUsername(vendorMailProperties.getUsername());
        mailSender.setPassword(vendorMailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put(vendorMailProperties.getAuth(), "true");
        props.put(vendorMailProperties.getStarttls(), "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean(name = "updatePasswordMessage")
    public SimpleMailMessage constructUpdatePasswordEmailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Please visit %s and activate your Registration:\n\n"
                        + "Initial UserId and Password to log into the portal are:\n\n"
                        + "User ID : %s\n"
                        + "Initial Login Password : %s"
                        + "\n\n\n Please note: On successfull login to activate your registration " +
                        "you will have to change your password and login again");
        return message;
    }

    @Bean(name = "resetTokenMessage")
    public SimpleMailMessage constructResetTokenEmailMessage() {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setText("Please click the Link below to reset password" + "\n\n" + "%s" + "/changePassword?id=" + "%s" + "&token=" + "%s");
        return emailMessage;
    }

    @Bean(name = "loginReminderMessage")
    public SimpleMailMessage constructLoginReminderEmailMessage() {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setText("It has been long since you have first logged in into the vendor management portal" + "\n\n" + "Please visit the Vendor Management portal " +
                "at %s and complete your vendor registration information at the earliest");
        return emailMessage;
    }
}