package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.masterdata.HsnMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HsnMasterRepository extends JpaRepository<HsnMaster, String> {
    HsnMaster findByCode(String code);

    List<HsnMaster> findAll();
}
