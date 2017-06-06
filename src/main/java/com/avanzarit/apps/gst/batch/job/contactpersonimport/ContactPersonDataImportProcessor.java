package com.avanzarit.apps.gst.batch.job.contactpersonimport;

import com.avanzarit.apps.gst.model.ContactPersonMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ContactPersonDataImportProcessor implements ItemProcessor<ContactPersonMaster, ContactPersonMaster> {

    private static final Logger log = LoggerFactory.getLogger(ContactPersonDataImportProcessor.class);

    @Override
    public ContactPersonMaster process(ContactPersonMaster contactPersonMaster) throws Exception {

        return contactPersonMaster;
    }
}
