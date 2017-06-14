package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerImportJobListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(CustomerImportJobListener.class);

    private final CustomerRepository customerRepository;

    public CustomerImportJobListener(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Finish Job! Check the results");

            List<Customer> customers = customerRepository.findAll();

            for (Customer customer : customers) {
                log.info("Found <" + customer + "> in the database.");
            }
        }
    }
}
