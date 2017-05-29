package com.avanzarit.apps.gst.batch.job.vendorexport;

import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by AVANZAR on 5/17/2017.
 */
public class VendorDataExportProcessor implements ItemProcessor<Vendor, Vendor> {

    @Override
    public Vendor process(Vendor result) throws Exception {
        System.out.println("Processing result :" + result);
        return result;
    }
}
