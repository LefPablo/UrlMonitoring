package org.monitoring.endpoint;

import org.monitoring.schedule.UrlAvailabilitySchedule;
import org.monitoring.service.UrlMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("monitoring")
public class UrlMonitoringController { //TODO wrap responses

    private static final String SCHEDULED_TASK = "urlAvailabilitySchedule";

    private final UrlAvailabilitySchedule scheduledTask;
    private final UrlMonitoringService monitoringService;
    private final ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    public UrlMonitoringController(UrlAvailabilitySchedule scheduledTask, UrlMonitoringService monitoringService, ScheduledAnnotationBeanPostProcessor postProcessor) {
        this.scheduledTask = scheduledTask;
        this.monitoringService = monitoringService;
        this.postProcessor = postProcessor;
    }

    @GetMapping(path = "/start", produces = "application/json")
    public String startMonitoring() {
        postProcessor.postProcessAfterInitialization(scheduledTask, SCHEDULED_TASK);
        return "OK";
    }

    @GetMapping(path = "/stop", produces = "application/json")
    public String stopMonitoring() {
        postProcessor.postProcessBeforeDestruction(scheduledTask, SCHEDULED_TASK);
        return "OK";
    }

    @GetMapping(path = "/result", produces = "application/json")
    public boolean getResult() {
        return monitoringService.getLastResult();
    }
}
