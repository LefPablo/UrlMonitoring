package org.monitoring.schedule;

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

    @Value("${url}")
    private String urlToCheck;

    private final UrlMonitoringService monitoringService;

    private final Logger logger = LoggerFactory.getLogger(UrlAvailabilitySchedule.class);

    @Autowired
    public UrlAvailabilitySchedule(UrlMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Scheduled(cron= "${cron.expression}")
    public void checkUrlAvailability() {
        try {
            URL url = new URL(urlToCheck);
            boolean checkStatus = monitoringService.isUrlAvailable(url);
            logger.info("URL: {}, available: {}", urlToCheck, checkStatus);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Incorrect URL: %s", urlToCheck), e);
        }
    }
}
