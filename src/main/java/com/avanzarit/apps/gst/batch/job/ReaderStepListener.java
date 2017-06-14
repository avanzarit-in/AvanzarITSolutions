package com.avanzarit.apps.gst.batch.job;

import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;

public abstract class ReaderStepListener<T> {

    private static final Logger LOGGER = LogManager.getLogger(ReaderStepListener.class);
    private BatchLog batchLog;
    private StepExecution stepExecution;

    public void onReadError(Exception exception) {
        LOGGER.error(exception.getMessage());
        LOGGER.error(exception.getCause().getMessage());
        batchLog.log(exception.getMessage());
        batchLog.log(exception.getCause().getMessage());
    }

    public void beforeRead() {
        batchLog.log("----------------------------");
    }

    public void afterReadItem(T item) {
        LOGGER.info("Read Item :{}", item.toString());
        batchLog.log("Successfully Read Item " + item.toString());
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
