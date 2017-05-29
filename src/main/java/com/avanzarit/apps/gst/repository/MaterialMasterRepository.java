package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface MaterialMasterRepository extends JpaRepository<MaterialMaster, String> {
    MaterialMaster findById(String id);

    List<MaterialMaster> findByVendor(Vendor vendor);
}