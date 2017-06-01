package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialImportJobListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(MaterialImportJobListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Finish Job! Check the results");
        }
    }
}
