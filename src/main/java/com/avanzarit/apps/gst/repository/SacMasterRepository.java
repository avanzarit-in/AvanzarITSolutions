package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.SacMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SacMasterRepository extends JpaRepository<SacMaster, String> {
    SacMaster findByCode(String code);

    List<SacMaster> findAll();
}
