package org.monitoring.service;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmailServiceTest {

    @Test
    void sendReportTemplate() throws NoSuchFieldException, IllegalAccessException {
        //given
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("URL monitoring service report\\nLast checking result \\nurl: %s\\navailable: %s");
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailService service = new EmailService(message, mailSender);
        Field fromField = service.getClass().getDeclaredField("from");
        fromField.setAccessible(true);
        fromField.set(service, "sender");

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("sender");
        expectedMessage.setTo("receiver");
        expectedMessage.setSubject("testing");
        expectedMessage.setText("URL monitoring service report\\nLast checking result \\nurl: some url\\navailable: true");
        //when
        service.sendReportTemplate("receiver", "testing", "some url", "true");

        //then
        verify(mailSender).send(expectedMessage);
    }

}