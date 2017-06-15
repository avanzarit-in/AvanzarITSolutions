package com.avanzarit.apps.gst.email;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by SPADHI on 5/25/2017.
 */
public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text,
                           MAIL_SENDER mailSender);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        MAIL_SENDER mailSender,
                                        SimpleMailMessage template,
                                        String ...templateArgs);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   MAIL_SENDER mailSender,
                                   String text,
                                   String pathToAttachment);
}
