package com.avanzarit.apps.gst.batch.job.contactpersonimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class ContactPersonFieldSetMapper implements FieldSetMapper<ContactPersonMaster> {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public ContactPersonMaster mapFieldSet(FieldSet fieldSet) throws BindException {

        String vendorId = fieldSet.readString("VENDORID");
        if (vendorId == null) {
            throw new SkippableReadException("Vendor ID can not be blank in the import file not uploading");
        }
        if (vendorRepository.findByVendorId(vendorId) == null) {
            throw new SkippableReadException("Vendor ID " + vendorId + " not found in vendor master table not uploading");
        }
        ContactPersonMaster result = new ContactPersonMaster();
        result.setVendorId(vendorId);
        result.setLastName(fieldSet.readString("LASTNAME"));
        result.setFirstName(fieldSet.readString("FIRSTNAME"));
        result.setDepartment(fieldSet.readString("DEPARTMENT"));
        result.setTelephone(fieldSet.readString("TELEPHONE"));
        result.setMobile(fieldSet.readString("MOBILE"));
        result.setEmail(fieldSet.readString("EMAIL"));
        return result;
    }
}
