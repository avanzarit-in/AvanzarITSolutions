package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerContactPersonMasterRepository extends JpaRepository<CustomerContactPersonMaster, String> {
    CustomerContactPersonMaster findById(String id);

    List<CustomerContactPersonMaster> findByCustomer(Customer customer);
}

