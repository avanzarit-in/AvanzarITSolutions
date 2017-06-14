package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.batch.job.WriterStepListener;
import com.avanzarit.apps.gst.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerImportWriterStepListener extends WriterStepListener<Customer> {

    private static final Logger LOGGER = LogManager.getLogger(CustomerImportWriterStepListener.class);

    @OnWriteError
    public void onWriteError(Exception exception, List<Customer> items) {
        super.onWriteError(exception, items);
    }

    @AfterWrite
    public void afterWrite(List<Customer> item) {
        super.afterWriteItem(item);
    }


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        super.beforeStep(stepExecution);
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        super.afterStep();
        return ExitStatus.COMPLETED;
    }
}
