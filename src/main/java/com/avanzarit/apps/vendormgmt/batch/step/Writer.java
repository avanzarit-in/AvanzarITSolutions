package com.avanzarit.apps.vendormgmt.batch.step;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Writer implements ItemWriter<Vendor> {

    private VendorRepository vendorRepository;

    private UserService userService;


    public Writer(VendorRepository vendorRepository, UserService userService) {
        this.vendorRepository = vendorRepository;
        this.userService = userService;
    }

    @Override
    public void write(List<? extends Vendor> vendors) throws Exception {


        for (Vendor vendor : vendors) {
            if (!vendor.getVendorId().equals("admin")) {
                vendorRepository.save(vendor);
            }
            if (userService.findByUsername(vendor.getVendorId()) == null) {
                User user = new User();
                user.setUsername(vendor.getVendorId());
                user.setPassword("welcome123");
                user.setPasswordConfirm("welcome123");
                userService.save(user);
            }


        }

    }

}
