package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.properties.AppProperties;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VendorDataImportWriter implements ItemWriter<Vendor> {

    private VendorRepository vendorRepository;
    private UserService userService;
    public SimpleMailMessage template;
    public EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;

    @Autowired
    public void setDefaultPassword(UserProperties properties) {
        this.defaultPassword = properties.getDefaultPassword();
    }

    @Autowired
    public void setContextURL(AppProperties properties) {
        this.contextURL = properties.getContextURL();
    }

    public VendorDataImportWriter(VendorRepository vendorRepository, UserService userService, SimpleMailMessage template, EmailServiceImpl emailService) {
        this.vendorRepository = vendorRepository;
        this.userService = userService;
        this.template = template;
        this.emailService = emailService;
    }

    @Override
    public void write(List<? extends Vendor> vendors) throws Exception {
        for (Vendor vendor : vendors) {

            List<MaterialMaster> materialMasterList = new ArrayList<>();
            MaterialMaster materialMaster = new MaterialMaster();
            materialMaster.setVendor(vendor);
            materialMasterList.add(materialMaster);
            vendor.setMaterial(materialMasterList);
            vendorRepository.save(vendor);

            if (userService.findByUsername(vendor.getVendorId()) == null) {
                User user = new User();
                user.setUsername(vendor.getVendorId());
                user.setPassword(defaultPassword);
                user.setEmail(vendor.getEmail());
                user.setUserStatus(UserStatusEnum.NEW);
                userService.save(user);

                if (!StringUtils.isEmpty(user.getEmail())) {
                    String text = String.format(template.getText(), contextURL, user.getUsername(), defaultPassword);
                    emailService.sendSimpleMessage(user.getEmail(), "Welcome to Vendor Management Portal", text);
                }
            }
        }
    }
}
