package org.monitoring.service;

import org.apache.logging.log4j.util.Strings;
import org.monitoring.constants.ConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public final SimpleMailMessage template;
    private final JavaMailSender emailSender;
    private final String from;

    @Autowired
    public EmailService(SimpleMailMessage template, JavaMailSender emailSender) {
        this.template = template;
        this.emailSender = emailSender;
        this.from = System.getProperty(ConfigConstants.EMAIL_FROM);
    }

    public void sendReportTemplate(String to, String subject, String... templateArgs) {
        if (Strings.isEmpty(to)) {
            throw new IllegalArgumentException("Recipient should not be empty");
        }
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
