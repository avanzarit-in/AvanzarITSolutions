package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.model.UserStatusEnum;
import com.avanzarit.apps.gst.auth.properties.UserProperties;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.properties.AppProperties;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class VendorDataImportWriter implements ItemWriter<Vendor> {

    private UserService userService;
    private RoleRepository roleRepository;
    private VendorRepository vendorRepository;
    private SimpleMailMessage template;
    private EmailServiceImpl emailService;
    private String defaultPassword;
    private String contextURL;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setTemplate(SimpleMailMessage updatePasswordMessage) {
        this.template = updatePasswordMessage;
    }

    @Autowired
    public void setEmailService(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setDefaultPassword(UserProperties properties) {
        this.defaultPassword = properties.getDefaultPassword();
    }

    @Autowired
    public void setContextURL(AppProperties properties) {
        this.contextURL = properties.getContextURL();

    }

    @Override
    public void write(List<? extends Vendor> vendors) throws Exception {
        for (Vendor vendor : vendors) {

            List<MaterialMaster> materialMasterList = new ArrayList<>();
            MaterialMaster materialMaster = new MaterialMaster();
            materialMaster.setVendor(vendor);
            materialMasterList.add(materialMaster);

            List<ContactPersonMaster> contactPersonMasterList = new ArrayList<>();
            ContactPersonMaster contactPersonMaster = new ContactPersonMaster();
            contactPersonMaster.setVendor(vendor);
            contactPersonMasterList.add(contactPersonMaster);

            vendor.setMaterialMaster(materialMasterList);
            vendor.setContactPersonMaster(contactPersonMasterList);
            vendorRepository.save(vendor);

            if (userService.findByUsername(vendor.getVendorId()) == null) {
                Role vendorRole = roleRepository.findByName("VENDOR");
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(vendorRole);
                User user = new User();
                user.setUsername(vendor.getVendorId());
                user.setPassword(defaultPassword);
                user.setEmail(vendor.getEmail());
                user.setUserStatus(UserStatusEnum.NEW);
                user.setRoles(roleSet);
                userService.save(user);

                if (!StringUtils.isEmpty(user.getEmail())) {
                    String text = String.format(template.getText(), contextURL, user.getUsername(), defaultPassword);
                    emailService.sendSimpleMessage(user.getEmail(), "Welcome to Vendor Management Portal", text);
                }
            }
        }
    }
}
