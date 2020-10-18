package ru.iworking.personnel.reserve.batch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

    private static final Logger logger = LogManager.getLogger(JobCompletionListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
        } else if(jobExecution.getStatus() == BatchStatus.FAILED){
            logger.error("BATCH JOB FAILED WITH FOLLOWING EXCEPTIONS ");
            List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
            for(Throwable th : exceptionList){
                logger.error("exception :" + th.getLocalizedMessage());
            }
        }
    }

}
