package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.HsnMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface HsnMasterRepository extends JpaRepository<HsnMaster, String> {
    HsnMaster findById(String id);

    List<HsnMaster> findAll();
}
