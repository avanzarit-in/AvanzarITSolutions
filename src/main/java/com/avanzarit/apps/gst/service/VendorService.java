package com.avanzarit.apps.gst.service;

import com.avanzarit.apps.gst.model.Vendor;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface VendorService {
    void save(Vendor user);

    Vendor findByVendorId(String vendorId);
}