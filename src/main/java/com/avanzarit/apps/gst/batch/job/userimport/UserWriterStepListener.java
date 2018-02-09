package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.batch.job.WriterStepListener;
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
public class UserWriterStepListener extends WriterStepListener<DbUser> {

    private static final Logger LOGGER = LogManager.getLogger(UserWriterStepListener.class);

    @OnWriteError
    public void onWriteError(Exception exception, List<DbUser> items) {
        super.onWriteError(exception, items);
    }

    @AfterWrite
    public void afterWrite(List<DbUser> item) {
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
