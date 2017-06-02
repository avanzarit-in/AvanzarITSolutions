package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.batch.job.ReaderStepListener;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorExportReaderStepListener;
import com.avanzarit.apps.gst.model.MaterialMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.stereotype.Component;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class MaterialImportReaderStepListener extends ReaderStepListener<MaterialMaster>{

    private static final Logger LOGGER = LogManager.getLogger(MaterialImportReaderStepListener.class);
    private StepExecution stepExecution;


    @OnReadError
    public void onReadError(Exception exception) {
        super.onReadError(exception);
    }

    @AfterRead
    public void afterRead(MaterialMaster item) {
        super.afterReadItem(item);
    }

    @Override
    public StepExecution getStepExecution() {
        return this.stepExecution;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }


    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }


}
