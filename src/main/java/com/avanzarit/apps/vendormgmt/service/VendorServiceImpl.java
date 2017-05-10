package com.avanzarit.apps.vendormgmt.service;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.repository.RoleRepository;
import com.avanzarit.apps.vendormgmt.auth.repository.UserRepository;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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
    public Vendor findByVendorName(String vendorName) {
        return vendorRepository.findByName(vendorName);
    }
}
