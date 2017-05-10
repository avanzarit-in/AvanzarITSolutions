package com.avanzarit.apps.vendormgmt.service;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.model.Vendor;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface VendorService {
    void save(Vendor user);

    Vendor findByVendorName(String vendorName);
}
