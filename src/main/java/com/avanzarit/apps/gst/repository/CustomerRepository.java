package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByCustomerId(String customerId);
}
