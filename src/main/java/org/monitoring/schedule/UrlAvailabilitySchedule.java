package org.monitoring.schedule;

import org.monitoring.constants.ConfigConstants;
import org.monitoring.service.EmailService;
import org.monitoring.service.UrlMonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlAvailabilitySchedule {

    @Value("${email.subject}")
    private String subject;
    private final String urlToCheck;
    private final String to;

    private final UrlMonitoringService monitoringService;
    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(UrlAvailabilitySchedule.class);

    @Autowired
    public UrlAvailabilitySchedule(UrlMonitoringService monitoringService, EmailService emailService) {
        this.monitoringService = monitoringService;
        this.emailService = emailService;
        this.urlToCheck = System.getProperty(ConfigConstants.URL_TO_MONITOR);
        this.to = System.getProperty(ConfigConstants.EMAIL_TO);
    }

    @Scheduled(cron= "#{environment.CRON_EXPRESSION}")
    public void checkUrlAvailability() {
        try {
            URL url = new URL(urlToCheck);
            boolean checkStatus = monitoringService.isUrlAvailable(url);
            emailService.sendReportTemplate(to, subject, urlToCheck, String.valueOf(checkStatus));
            logger.info("URL: {}, available: {}", urlToCheck, checkStatus);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Incorrect URL: %s", urlToCheck), e);
        }
    }
}
