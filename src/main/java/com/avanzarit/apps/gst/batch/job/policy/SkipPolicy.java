package com.avanzarit.apps.gst.batch.job.policy;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;

public class SkipPolicy extends AlwaysSkipItemSkipPolicy {

    private static final Logger LOGGER = LogManager.getLogger(SkipPolicy.class);
    @Override
    public boolean shouldSkip(java.lang.Throwable t, int skipCount){

        if(t.getCause() instanceof SkippableReadException){
            return true;
        }
        return false;
    }
}
