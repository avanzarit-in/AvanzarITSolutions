package com.avanzarit.apps.gst.batch.job.contactpersonimport;

import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.ContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactPersonDataImportWriter implements ItemWriter<ContactPersonMaster> {

    private VendorRepository vendorRepository;
    private ContactPersonMasterRepository contactPersonMasterRepository;


    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setContactPersonMasterRepository(ContactPersonMasterRepository contactPersonMasterRepository) {
        this.contactPersonMasterRepository = contactPersonMasterRepository;
    }

    @Override
    public void write(List<? extends ContactPersonMaster> contactPersonMasters) throws Exception {
        for (ContactPersonMaster contactPersonMaster : contactPersonMasters) {
            Vendor vendor = vendorRepository.findByVendorId(contactPersonMaster.getVendorId());
            if (vendor != null) {
                List<ContactPersonMaster> contactPersonMastersList = vendor.getContactPersonMaster();
                if (!contactPersonMastersList.contains(contactPersonMaster)) {
                    contactPersonMaster.setVendor(vendor);
                    contactPersonMasterRepository.save(contactPersonMaster);
                }
            }
        }
    }
}
