package com.avanzarit.apps.vendormgmt.batch.job.vendorimport;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.model.UserStatusEnum;
import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import com.avanzarit.apps.vendormgmt.email.EmailServiceImpl;
import com.avanzarit.apps.vendormgmt.model.MaterialMaster;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VendorDataImportWriter implements ItemWriter<Vendor> {

    private VendorRepository vendorRepository;

    private UserService userService;
    public SimpleMailMessage template;
    public EmailServiceImpl emailService;


    public VendorDataImportWriter(VendorRepository vendorRepository, UserService userService, SimpleMailMessage template, EmailServiceImpl emailService) {
        this.vendorRepository = vendorRepository;
        this.userService = userService;
        this.template = template;
        this.emailService = emailService;
    }

    @Override
    public void write(List<? extends Vendor> vendors) throws Exception {
        for (Vendor vendor : vendors) {
            if (!vendor.getVendorId().equals("admin")) {
                List<MaterialMaster> materialMasterList = new ArrayList<>();
                MaterialMaster materialMaster = new MaterialMaster();
                materialMaster.setVendor(vendor);
                materialMasterList.add(materialMaster);
                vendor.setMaterial(materialMasterList);
                vendorRepository.save(vendor);
            }
            if (userService.findByUsername(vendor.getVendorId()) == null) {
                User user = new User();
                user.setUsername(vendor.getVendorId());
                user.setPassword("welcome123");
                user.setPasswordConfirm("welcome123");
                user.setEmail(vendor.getEmail());
                user.setUserStatus(UserStatusEnum.NEW);
                userService.save(user);

                if (!StringUtils.isEmpty(user.getEmail())) {
                    String text = String.format(template.getText(), "http://localhost:8080/login", user.getUsername(), "welcome123");
                    emailService.sendSimpleMessage(user.getEmail(), "Welcome to Vendor Management Portal", text);
                }
            }
        }
    }
}
