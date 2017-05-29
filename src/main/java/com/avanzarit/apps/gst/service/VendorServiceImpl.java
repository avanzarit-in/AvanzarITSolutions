package com.avanzarit.apps.gst.service;

import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Service
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;


@Override
    public void save(Vendor vendor) {

        vendorRepository.save(vendor);
    }



    @Override
    public Vendor findByVendorId(String vendorId) {
        return vendorRepository.findByVendorId(vendorId);
    }
}
