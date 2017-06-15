package com.avanzarit.apps.gst.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by SPADHI on 5/25/2017.
 */
@Component
public class EmailServiceImpl implements EmailService {


    @Autowired
    public JavaMailSender javaVendorMailSender;
    @Autowired
    public JavaMailSender javaCustomerMailSender;
    @Autowired
    public VendorMailProperties vendorMailProperties;
    @Autowired
    public CustomerMailProperties customerMailProperties;

    public void sendSimpleMessage(String to, String subject, String text, MAIL_SENDER mailSender) throws MailException {

        boolean sendMail = false;
        String fromEmailId = null;
        JavaMailSender javaMailSender = null;
        if (mailSender == MAIL_SENDER.CUSTOMER) {
            fromEmailId = customerMailProperties.getFromMailId();
            sendMail = customerMailProperties.isSendEmail();
            javaMailSender = javaCustomerMailSender;
        } else if (mailSender == MAIL_SENDER.VENDOR) {
            fromEmailId = vendorMailProperties.getFromMailId();
            sendMail = vendorMailProperties.isSendEmail();
            javaMailSender = javaVendorMailSender;
        }

        if (fromEmailId != null && javaMailSender != null && sendMail) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmailId);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
        }
        throw new RuntimeException("Error Sending mail 'fromEmailId==null' or 'javaMailSender==null' or 'sendMail==false' ");

    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to,
                                               String subject,
                                               MAIL_SENDER mailSender,
                                               SimpleMailMessage template,
                                               String... templateArgs) {
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text, mailSender);
    }

    @Override
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          MAIL_SENDER mailSender,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = javaVendorMailSender.createMimeMessage();

            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            javaVendorMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
