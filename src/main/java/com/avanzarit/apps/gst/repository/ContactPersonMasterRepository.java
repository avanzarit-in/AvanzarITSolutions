package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface ContactPersonMasterRepository extends JpaRepository<ContactPersonMaster, String> {
    ContactPersonMaster findById(String id);

    List<ContactPersonMaster> findByVendor(Vendor vendor);
}

