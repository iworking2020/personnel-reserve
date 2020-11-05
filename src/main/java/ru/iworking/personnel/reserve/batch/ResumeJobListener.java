package ru.iworking.personnel.reserve.batch;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.controller.ResumeListViewController;
import ru.iworking.personnel.reserve.controller.ResumeViewController;
import ru.iworking.personnel.reserve.service.ResumeService;

@Component
@RequiredArgsConstructor
public class ResumeJobListener extends JobExecutionListenerSupport {

    private static final Logger logger = LogManager.getLogger(ResumeJobListener.class);

    private final ResumeListViewController resumeListViewController;
    private final ResumeViewController resumeViewController;
    private final ResumeService resumeService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        resumeService.deleteAll();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            resumeViewController.actionCancel(null);
            resumeListViewController.actionUpdate(null);
        }
    }

}
