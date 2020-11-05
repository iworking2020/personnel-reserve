package ru.iworking.personnel.reserve.batch;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.controller.CompanyListViewController;
import ru.iworking.personnel.reserve.controller.VacancyListViewController;
import ru.iworking.personnel.reserve.service.CompanyService;
import ru.iworking.personnel.reserve.service.VacancyService;

@Component
@RequiredArgsConstructor
public class CompanyJobListener extends JobExecutionListenerSupport {

    private static final Logger logger = LogManager.getLogger(CompanyJobListener.class);

    private final CompanyListViewController companyListViewController;
    private final VacancyListViewController vacancyListViewController;

    private final CompanyService companyService;
    private final VacancyService vacancyService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        companyService.deleteAll();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            vacancyListViewController.actionBack(null);
            companyListViewController.actionUpdate(null);
        }
    }

}
