package com.avanzarit.apps.gst.batch.job.vendorexport;

import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPADHI on 6/1/2017.
 */
@Component
public class ContactPersonDataFieldExtractor implements FieldExtractor<Vendor> {
    /**
     * @param item
     * @return an array containing item's parts
     */
    @Override
    public Object[] extract(Vendor item) {
        List<Object> values = new ArrayList<Object>();
        BeanWrapperFieldExtractor<ContactPersonMaster> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"vendorId", "lastName", "firstName", "department", "mobile", "telephone", "email"});
        for (ContactPersonMaster contactPersonMaster : item.getContactPersonMaster()) {
            contactPersonMaster.setVendorId(item.getVendorId());
            values.add(extractor.extract(contactPersonMaster));
        }
        return values.toArray();
    }
}


