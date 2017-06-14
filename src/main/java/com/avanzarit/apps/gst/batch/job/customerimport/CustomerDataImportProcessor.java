package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataImportProcessor implements ItemProcessor<Customer, Customer> {

    private static final Logger log = LoggerFactory.getLogger(CustomerDataImportProcessor.class);

    @Override
    public Customer process(Customer customer) throws Exception {

        return customer;
    }
}
