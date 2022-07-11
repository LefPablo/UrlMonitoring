package org.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
@EnableScheduling
@PropertySource("classpath:project.properties")
public class AppConfig {

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