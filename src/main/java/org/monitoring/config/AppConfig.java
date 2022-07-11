package org.monitoring.config;

import org.monitoring.constants.ConfigConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableScheduling
public class AppConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(System.getProperty(ConfigConstants.SMTP_HOST));
        mailSender.setPort(Integer.parseInt(System.getProperty(ConfigConstants.SMTP_PORT)));

        mailSender.setUsername(System.getProperty(ConfigConstants.SMTP_USERNAME));
        mailSender.setPassword(System.getProperty(ConfigConstants.SMTP_PASSWORD));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage(@Value("${email.template}") String template) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(template);
        return message;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.monitoring.endpoint"))
                .paths(PathSelectors.any())
                .build();
    }

}