package com.avanzarit.apps.gst.batch.job.customercontactpersonimport;

import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerContactPersonDataImportProcessor implements ItemProcessor<CustomerContactPersonMaster, CustomerContactPersonMaster> {

    private static final Logger log = LoggerFactory.getLogger(CustomerContactPersonDataImportProcessor.class);

    @Override
    public CustomerContactPersonMaster process(CustomerContactPersonMaster customerContactPersonMaster) throws Exception {

        return customerContactPersonMaster;
    }
}
