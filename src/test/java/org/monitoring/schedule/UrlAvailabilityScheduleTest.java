package org.monitoring.schedule;

import org.junit.jupiter.api.Test;
import org.monitoring.service.EmailService;
import org.monitoring.service.UrlMonitoringService;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlAvailabilityScheduleTest {

    @Test
    void checkUrlAvailability() throws NoSuchFieldException, IllegalAccessException, MalformedURLException {
        //given
        URL url = new URL("https://google.com");
        UrlMonitoringService monitoringService = mock(UrlMonitoringService.class);
        EmailService emailService = mock(EmailService.class);
        UrlAvailabilitySchedule availabilitySchedule = new UrlAvailabilitySchedule(monitoringService, emailService);
        Field urlField = availabilitySchedule.getClass().getDeclaredField("urlToCheck");
        Field subjectField = availabilitySchedule.getClass().getDeclaredField("subject");
        Field toField = availabilitySchedule.getClass().getDeclaredField("to");
        urlField.setAccessible(true);
        subjectField.setAccessible(true);
        toField.setAccessible(true);
        urlField.set(availabilitySchedule, "https://google.com");
        subjectField.set(availabilitySchedule, "testing");
        toField.set(availabilitySchedule, "receiver");
        when(monitoringService.isUrlAvailable(url)).thenReturn(true);

        //when
        availabilitySchedule.checkUrlAvailability();

        //then
        verify(monitoringService).isUrlAvailable(url);
        verify(emailService).sendReportTemplate("receiver", "testing", "https://google.com", "true");
    }

}