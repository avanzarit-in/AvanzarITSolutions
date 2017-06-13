package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialMasterRepository extends JpaRepository<MaterialMaster, String> {
    MaterialMaster findById(long id);

    List<MaterialMaster> findByVendor(Vendor vendor);
}
