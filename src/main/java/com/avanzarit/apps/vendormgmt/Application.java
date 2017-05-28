package com.avanzarit.apps.vendormgmt;

import com.avanzarit.apps.vendormgmt.interceptor.ThymeleafLayoutInterceptor;
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
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Qualifier("dataSource")
    @Autowired
    DataSource datasource;

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
            public void customize(ConfigurableEmbeddedServletContainer container)
            {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

                container.addErrorPages(error404Page,error500Page);
            }
        };
    }

    @Bean(name = "javaMmailSender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("127.0.0.1");
        mailSender.setPort(587);

        mailSender.setUsername("admin@avanzarit.in");
        mailSender.setPassword("admin");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean(name = "updatePasswordMessage")
    public SimpleMailMessage constructUpdatePasswordEmailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Please visit %s and activate your Vendor Registration:\n\n"
        +"Initial UserId and Password to log into the portal are:\n\n"
        +"User ID : %s\n"
        +"Initial Login Password : %s"
        +"\n\n\n Please note: On successfull login to activate your registration " +
                        "you will have to change your password and login again");
        return message;
    }

    @Bean(name = "resetTokenMessage")
    public SimpleMailMessage constructResetTokenEmailMessage() {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setText("Please click the Link below to reset password" + "\n\n" + "%s" + "/changePassword?id=" + "%s" + "&token=" + "%s");
        return emailMessage;
    }
}