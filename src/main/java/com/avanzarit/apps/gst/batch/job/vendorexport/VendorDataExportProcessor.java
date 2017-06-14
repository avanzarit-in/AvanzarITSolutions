package com.avanzarit.apps.gst.batch.job.vendorexport;

import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class VendorDataExportProcessor implements ItemProcessor<Vendor, Vendor> {

    @Override
    public Vendor process(Vendor result) throws Exception {
        System.out.println("Processing result :" + result);
        return result;
    }
}
