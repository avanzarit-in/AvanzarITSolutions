package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.batch.job.WriterStepListener;
import com.avanzarit.apps.gst.model.Vendor;
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

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class VendorImportWriterStepListener extends WriterStepListener<Vendor> {

    private static final Logger LOGGER = LogManager.getLogger(VendorImportWriterStepListener.class);

    @OnWriteError
    public void onWriteError(Exception exception, List<Vendor> items) {
        super.onWriteError(exception, items);
    }

    @AfterWrite
    public void afterWrite(List<Vendor> item) {
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
