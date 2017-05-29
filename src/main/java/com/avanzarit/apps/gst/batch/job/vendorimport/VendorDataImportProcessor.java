package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class VendorDataImportProcessor implements ItemProcessor<Vendor, Vendor> {

    private static final Logger log = LoggerFactory.getLogger(VendorDataImportProcessor.class);

    @Override
    public Vendor process(Vendor vendor) throws Exception {

        return vendor;
    }
}
