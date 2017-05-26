package com.avanzarit.apps.vendormgmt.email;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by SPADHI on 5/25/2017.
 */
public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String ...templateArgs);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
}
