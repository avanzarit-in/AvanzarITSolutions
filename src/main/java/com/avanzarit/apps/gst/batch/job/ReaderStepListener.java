package com.avanzarit.apps.gst.batch.job;

import com.avanzarit.apps.gst.batch.job.userimport.UserReaderStepListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;


/**
 * Created by SPADHI on 6/2/2017.
 */
public abstract class ReaderStepListener<T> {

    private static final Logger LOGGER = LogManager.getLogger(UserReaderStepListener.class);

    public void onReadError(Exception exception) {
        LOGGER.error(exception.getMessage());
        LOGGER.error(exception.getCause().getMessage());
        String log = (String) getStepExecution().getJobExecution().getExecutionContext().get("log");
        StringBuilder sb = new StringBuilder(log == null ? "" : log);
        sb.append(exception.getMessage()).append("\n");
        sb.append(exception.getCause().getMessage()).append("\n");
        getStepExecution().getJobExecution().getExecutionContext() .put("log",sb.toString());
    }


    public void afterReadItem(T item) {
        LOGGER.info("Read Item :{}", item.toString());
        String log = (String) getStepExecution().getJobExecution().getExecutionContext().get("log");
        StringBuilder sb = new StringBuilder(log == null ? "" : log);
        sb.append("Successfully Read Item "+item.toString()).append("\n");
        getStepExecution().getJobExecution().getExecutionContext() .put("log",sb.toString());
    }

    public abstract StepExecution getStepExecution();


}
