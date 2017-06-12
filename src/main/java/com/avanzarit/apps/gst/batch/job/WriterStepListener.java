package com.avanzarit.apps.gst.batch.job;

import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.util.List;


public abstract class WriterStepListener<T> {

    private static final Logger LOGGER = LogManager.getLogger(WriterStepListener.class);
    private BatchLog batchLog;
    private StepExecution stepExecution;

    public void onWriteError(Exception exception, List<T> items) {
        LOGGER.error(exception.getMessage());
        LOGGER.error(exception.getCause().getMessage());
        batchLog.log(exception.getMessage());
        batchLog.log(exception.getCause().getMessage());
    }


    public void afterWriteItem(List<T> items) {
        for (T item : items) {
            LOGGER.info("Write Item :{}", item.toString());
            batchLog.log("Successfully Written Item " + item.toString());
        }
    }

    public void updateLog() {
        stepExecution.getJobExecution().getExecutionContext().put("log", batchLog);
    }

    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        this.batchLog = new BatchLog();
        stepExecution.getJobExecution().getExecutionContext().put("log", batchLog);
    }

    public void afterStep() {
        updateLog();
    }


}
