package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
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
public class UserWriterStepListener {

    private static final Logger LOGGER = LogManager.getLogger(UserReaderStepListener.class);

    @OnWriteError
    public void onWriteError(Exception exception, List<User> items) {
        LOGGER.error( exception.getMessage());

    }

    @AfterWrite
    public void afterWrite(List<? extends User> items) {
        for(User user:items) {
            LOGGER.info("Write Item :{}", user.getUsername());
        }
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {

        return ExitStatus.COMPLETED;
    }
}
