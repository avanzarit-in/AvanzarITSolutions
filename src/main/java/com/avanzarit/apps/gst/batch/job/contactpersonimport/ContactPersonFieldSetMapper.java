package com.avanzarit.apps.gst.batch.job.contactpersonimport;

import com.avanzarit.apps.gst.model.ContactPersonMaster;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class ContactPersonFieldSetMapper implements FieldSetMapper<ContactPersonMaster> {


    @Override
    public ContactPersonMaster mapFieldSet(FieldSet fieldSet) throws BindException {
        ContactPersonMaster result = new ContactPersonMaster();
        result.setVendorId(fieldSet.readString("VENDORID"));
        result.setLastName(fieldSet.readString("LASTNAME"));
        result.setFirstName(fieldSet.readString("FIRSTNAME"));
        result.setDepartment(fieldSet.readString("DEPARTMENT"));
        result.setTelephone(fieldSet.readString("TELEPHONE"));
        result.setMobile(fieldSet.readString("MOBILE"));
        result.setEmail(fieldSet.readString("EMAIL"));

        return result;
    }
}
