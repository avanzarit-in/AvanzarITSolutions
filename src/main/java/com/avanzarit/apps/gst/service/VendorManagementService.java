package com.avanzarit.apps.gst.service;

import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.db.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.db.repository.UserRepository;
import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.ServiceSacMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.VendorStatusEnum;
import com.avanzarit.apps.gst.repository.AttachmentRepository;
import com.avanzarit.apps.gst.repository.ContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.HsnMasterRepository;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.SacMasterRepository;
import com.avanzarit.apps.gst.repository.ServiceSacMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import com.avanzarit.apps.gst.storage.StorageService;
import com.avanzarit.apps.gst.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorManagementService {

    @Autowired
    private HsnMasterRepository hsnMasterRepository;

    @Autowired
    private SacMasterRepository sacMasterRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MaterialMasterRepository materialMasterRepository;

    @Autowired
    private ServiceSacMasterRepository serviceSacMasterRepository;

    @Autowired
    private ContactPersonMasterRepository contactPersonMasterRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    AttachmentRepository attachmentRepository;

    public void save(Vendor vendor, String userName) {
        List<MaterialMaster> materials = vendor.getMaterialMaster();
        List<MaterialMaster> cleanedMaterailList = materials.stream()
                .filter(line -> line.getId() != null)
                .collect(Collectors.toList());

        List<ServiceSacMaster> serviceSacMasters = vendor.getServiceSacMaster();
        List<ServiceSacMaster> cleanedServiceSaclList = serviceSacMasters.stream()
                .filter(line -> line.getId() != null)
                .collect(Collectors.toList());

        List<ContactPersonMaster> contactPersonMasters = vendor.getContactPersonMaster();
        List<ContactPersonMaster> cleanedContactPersonMasters = contactPersonMasters.stream()
                .filter(line -> line.getId() != null)
                .collect(Collectors.toList());

        for (MaterialMaster material : cleanedMaterailList) {
            if (material.getId().equals(999L)) {
                material.setId(null);
            } else {
                MaterialMaster materialDetails = materialMasterRepository.findById(material.getId());
                material.setCode(materialDetails.getCode());
                material.setDesc(materialDetails.getDesc());
                material.setVendor(vendor);
                materialMasterRepository.save(material);
            }
        }
        for (ServiceSacMaster serviceSacMaster : cleanedServiceSaclList) {
            if (serviceSacMaster.getId().equals(999L)) {
                serviceSacMaster.setId(null);
            }
            serviceSacMaster.setVendor(vendor);
            serviceSacMasterRepository.save(serviceSacMaster);
        }
        for (ContactPersonMaster contactPersonMaster : cleanedContactPersonMasters) {
            if (contactPersonMaster.getId().equals(999L)) {
                contactPersonMaster.setId(null);
            }
            contactPersonMaster.setVendor(vendor);
            contactPersonMasterRepository.save(contactPersonMaster);
        }

        Vendor oldVendor = vendorRepository.findByVendorId(vendor.getVendorId());

        if (oldVendor.getVendorStatus() != VendorStatusEnum.SUBMITTED_APPROVAL) {
            if (vendor.getSubmityn().equalsIgnoreCase("Y")) {
                vendor.setSubmittedBy(userName);
                vendor.setLastSubmittedOn(new Date());
            } else {
                vendor.setModifiedBy(userName);
                vendor.setLastModifiedOn(new Date());
            }
        }

        copyOverProperties(vendor, oldVendor);
        if (oldVendor.getVendorStatus() != VendorStatusEnum.SUBMITTED_APPROVAL) {
            if (vendor.getSubmityn().equalsIgnoreCase("Y")) {
                vendor.setSubmittedBy(userName);
                vendor.setLastSubmittedOn(new Date());
                vendor.setVendorStatus(VendorStatusEnum.SUBMITTED);
            } else {
                vendor.setModifiedBy(userName);
                vendor.setLastModifiedOn(new Date());
                vendor.setVendorStatus(VendorStatusEnum.MODIFIED);
            }
        }

        vendorRepository.save(vendor);


        List<MaterialMaster> materialList = materialMasterRepository.findByVendor(vendor);
        for (MaterialMaster material : materialList) {
            if (!cleanedMaterailList.contains(material)) {
                materialMasterRepository.delete(material);
            }
        }

        List<ServiceSacMaster> serviceSacMasterList = serviceSacMasterRepository.findByVendor(vendor);
        for (ServiceSacMaster serviceSacMaster : serviceSacMasterList) {
            if (!cleanedServiceSaclList.contains(serviceSacMaster)) {
                serviceSacMasterRepository.delete(serviceSacMaster);
            }
        }

        List<ContactPersonMaster> contactPersonMasterList = contactPersonMasterRepository.findByVendor(vendor);
        for (ContactPersonMaster contactPersonMaster : contactPersonMasterList) {
            if (!cleanedContactPersonMasters.contains(contactPersonMaster)) {
                contactPersonMasterRepository.delete(contactPersonMaster);
            }
        }

        DbUser dbUser = userRepository.findByUsername(vendor.getVendorId());
        if (dbUser != null) {
            dbUser.setEmail(vendor.getEmail());
            dbUser.setTelephone(vendor.getTelephoneNumberExtn());
            dbUser.setMobile(vendor.getMobileNo());
            userRepository.save(dbUser);
        }
    }

    private void copyOverProperties(Vendor newVendor, Vendor oldVendor) {
        List<Field> fields = Utils.findFields(Vendor.class, CopyOver.class);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(oldVendor);
                field.set(newVendor, value);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
