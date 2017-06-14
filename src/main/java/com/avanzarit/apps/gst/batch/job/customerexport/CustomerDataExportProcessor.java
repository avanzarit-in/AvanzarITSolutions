package com.avanzarit.apps.gst.batch.job.customerexport;

import com.avanzarit.apps.gst.model.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataExportProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer result) throws Exception {
        System.out.println("Processing result :" + result);
        return result;
    }
}
