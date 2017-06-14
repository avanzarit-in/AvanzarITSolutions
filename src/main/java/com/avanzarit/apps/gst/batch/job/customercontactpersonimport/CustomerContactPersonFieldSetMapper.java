package com.avanzarit.apps.gst.batch.job.customercontactpersonimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class CustomerContactPersonFieldSetMapper implements FieldSetMapper<CustomerContactPersonMaster> {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public CustomerContactPersonMaster mapFieldSet(FieldSet fieldSet) throws BindException {

        String customerId = fieldSet.readString("CUSTOMERID");
        if (customerId == null) {
            throw new SkippableReadException("Customer ID can not be blank in the import file not uploading");
        }
        if (vendorRepository.findByVendorId(customerId) == null) {
            throw new SkippableReadException("Customer ID " + customerId + " not found in vendor master table not uploading");
        }
        CustomerContactPersonMaster result = new CustomerContactPersonMaster();
        result.setCustomerId(customerId);
        result.setLastName(fieldSet.readString("LASTNAME"));
        result.setFirstName(fieldSet.readString("FIRSTNAME"));
        result.setDepartment(fieldSet.readString("DEPARTMENT"));
        result.setTelephone(fieldSet.readString("TELEPHONE"));
        result.setMobile(fieldSet.readString("MOBILE"));
        result.setEmail(fieldSet.readString("EMAIL"));
        return result;
    }
}
