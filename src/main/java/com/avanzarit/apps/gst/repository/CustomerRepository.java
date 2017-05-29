package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByCustomerId(String customerId);
}
