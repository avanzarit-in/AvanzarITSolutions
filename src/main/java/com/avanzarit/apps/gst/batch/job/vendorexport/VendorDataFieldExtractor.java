package com.avanzarit.apps.gst.batch.job.vendorexport;

import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.stereotype.Component;

/**
 * Created by SPADHI on 6/1/2017.
 */
@Component
public class VendorDataFieldExtractor implements FieldExtractor<Vendor> {
    /**
     * @param item
     * @return an array containing item's parts
     */
    @Override
    public Object[] extract(Vendor item) {
        BeanWrapperFieldExtractor<Vendor> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"vendorId", "vendorName1", "vendorName2", "vendorName3", "telephoneNumberExtn", "mobileNo", "email", "faxNumberExtn", "buildingNo", "address1", "address2", "address3", "address4", "address5", "city", "postCode", "region", "country", "accountHolderName", "accountNumber", "pan", "vatNumber"});
        return extractor.extract(item);
    }
}
