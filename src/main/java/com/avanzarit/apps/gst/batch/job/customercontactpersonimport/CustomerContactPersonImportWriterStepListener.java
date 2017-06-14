package com.avanzarit.apps.gst.batch.job.customercontactpersonimport;

import com.avanzarit.apps.gst.batch.job.WriterStepListener;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
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
public class CustomerContactPersonImportWriterStepListener extends WriterStepListener<CustomerContactPersonMaster> {

    private static final Logger LOGGER = LogManager.getLogger(CustomerContactPersonImportWriterStepListener.class);

    @OnWriteError
    public void onWriteError(Exception exception, List<CustomerContactPersonMaster> items) {
        super.onWriteError(exception, items);
    }

    @AfterWrite
    public void afterWrite(List<CustomerContactPersonMaster> item) {
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
