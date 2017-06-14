package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    Vendor findByVendorId(String vendorId);
}
