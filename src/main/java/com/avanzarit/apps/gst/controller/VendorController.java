package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.model.*;
import com.avanzarit.apps.gst.repository.*;
import com.avanzarit.apps.gst.storage.StorageService;
import com.avanzarit.apps.gst.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by SPADHI on 5/3/2017.
 */
@Controller
class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private HsnMasterRepository hsnMasterRepository;

    @Autowired
    private SacMasterRepository sacMasterRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

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


    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public String showVendor(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        String userName = auth.getUsername();
        Vendor vendor = vendorRepository.findByVendorId(userName);
        if (vendor == null) {
            redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
            return "redirect:/404";
        } else {
            model.addAttribute("vendor", vendor);
        }
        return "vendorForm";
    }

    @RequestMapping(path = "/action", method = RequestMethod.POST)
    public String resetVendorStatus(RedirectAttributes redirectAttributes,
                                    @RequestParam("vendorId") String vendorId,
                                    @RequestParam("action") String action) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        Vendor vendor = vendorRepository.findByVendorId(vendorId);
        if (vendor != null) {
            if (action.equalsIgnoreCase("APPROVE")) {
                vendor.setSubmityn("Y");
                vendor.setApprovedBy(userName);
                vendor.setLastApprovedOn(new Date());
                vendor.setVendorStatus(VendorStatusEnum.APPROVED);
                vendorRepository.save(vendor);
                redirectAttributes.addFlashAttribute("message", "Vendor Data Approved Successfully");

            } else if (action.equalsIgnoreCase("REJECT")) {
                vendor.setSubmityn("N");
                vendor.setRejectedBy(userName);
                vendor.setLastRejectedOn(new Date());
                vendor.setVendorStatus(VendorStatusEnum.REJECTED);
                vendorRepository.save(vendor);
                redirectAttributes.addFlashAttribute("message", "Vendor Data Rejected Successfully");

            } else if (action.equalsIgnoreCase("RESET")) {
                vendor.setSubmityn("N");
                vendor.setLastRevertedBy(userName);
                vendor.setLastRevertedOn(new Date());
                vendor.setRevertCount(vendor.getRevertCount() + 1);
                vendor.setRevertReason("Manual Action");
                vendor.setVendorStatus(VendorStatusEnum.REVERTED);
                vendorRepository.save(vendor);
                redirectAttributes.addFlashAttribute("message", "Vendor Data Reset Successfully");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Vendor Data could not be Reset contact support");
        }
        return "redirect:/vendorListView";
    }


    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/get/{vendorId}", method = RequestMethod.GET)
    public String showVendor(RedirectAttributes redirectAttributes, Model model, @PathVariable String vendorId) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("BUSINESS_OWNER") || (roles.contains("VENDOR") && auth.getUsername().equals(vendorId))) {
            Vendor vendor = vendorRepository.findByVendorId(vendorId);
            if (vendor == null) {
                redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
                return "redirect:/404";
            } else {
                if (roles.contains("BUSINESS_OWNER")) {
                    vendor.setSubmityn("Y");
                }
                model.addAttribute("vendor", vendor);
            }
            return "vendorForm";
        }
        return "redirect:/403";
    }

    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Vendor vendor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();

        String email = vendor.getEmail();
        if (email != null && email.indexOf(",") > 0) {
            email = email.substring(0, email.indexOf(","));
            vendor.setEmail(email);
        }
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

        if (vendor.getSubmityn().equalsIgnoreCase("Y")) {
            vendor.setSubmittedBy(userName);
            vendor.setLastSubmittedOn(new Date());
        } else {
            vendor.setModifiedBy(userName);
            vendor.setLastModifiedOn(new Date());
        }

        Vendor oldVendor = vendorRepository.findByVendorId(vendor.getVendorId());
        copyOverProperties(vendor, oldVendor);
        if (vendor.getSubmityn().equalsIgnoreCase("Y")) {
            vendor.setSubmittedBy(userName);
            vendor.setLastSubmittedOn(new Date());
            vendor.setVendorStatus(VendorStatusEnum.SUBMITTED);
        } else {
            vendor.setModifiedBy(userName);
            vendor.setLastModifiedOn(new Date());
            vendor.setVendorStatus(VendorStatusEnum.MODIFIED);
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

        redirectAttributes.addFlashAttribute("vendor", vendor);
        logger.debug("Forwarding to the exportVendorJob list...");
        if (vendor.getSubmityn() != null && vendor.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes hav been Saved!");
            return "redirect:get";
        }
        redirectAttributes.addFlashAttribute("message",
                "Successfully Submitted the changes");
        return "redirect:get";
    }


    @RequestMapping(value = {"/adminLanding"}, method = RequestMethod.GET)
    public String loadAdminLandingPage(Model model) {
        return "redirect:userListView";
    }

    @RequestMapping(value = {"/businessOwnerLanding"}, method = RequestMethod.GET)
    public String loadBusinessOwnerLandingPage(Model model) {
        return "redirect:vendorListView";
    }

    @RequestMapping(path = "/userListView", method = RequestMethod.GET)
    public ModelAndView showUserList() {
        logger.debug("in showUserList");

        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("userListView");
        return mav;
    }


    @RequestMapping(path = "/master/hsn/validate", method = RequestMethod.POST)
    public
    @ResponseBody
    String getHsnCode(@RequestParam Map<String, String> allRequestParams) {
        String hsnCode = allRequestParams.get("hsn");
        if (hsnMasterRepository.findByCode(hsnCode) != null) {
            return "{ \"valid\": true }";
        }
        return "{ \"valid\": false }";
    }

    @RequestMapping(path = "/master/sac/validate", method = RequestMethod.POST)
    @ResponseBody
    public String getSacCode(@RequestParam Map<String, String> allRequestParams) {
        String sacCode = allRequestParams.get("sac");
        if (sacMasterRepository.findByCode(sacCode) != null) {
            return "{ \"valid\": true }";
        }
        return "{ \"valid\": false }";
    }

    @RequestMapping(path = "/vendorListView", method = RequestMethod.GET)
    public ModelAndView showVendorList() {
        logger.debug("in showVendorList");

        ModelAndView mav = new ModelAndView();
        mav.addObject("vendors", vendorRepository.findAll());
        mav.setViewName("vendorListView");
        return mav;
    }

    @PostMapping("/uploadAttachment")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file_data") MultipartFile file, @RequestParam("docType") String docType,
                                   @RequestParam("vendorId") String vendorId,
                                   RedirectAttributes redirectAttributes) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        try {
            if (userName.equals(vendorId)) {
                storageService.deleteAll("attachment", userName + "/" + docType);
                storageService.store("attachment", userName + "/" + docType, file);
                Vendor vendor = vendorRepository.findByVendorId(vendorId);
                if (vendor != null) {
                    List<Attachment> attachments = vendor.getAttachments();
                    for (Attachment attachment : attachments) {
                        if (attachment.getDocType().equals(docType)) {
                            attachmentRepository.delete(attachment);
                        }
                    }
                    Attachment attachment = new Attachment();
                    attachment.setVendor(vendor);
                    attachment.setDocName(file.getOriginalFilename());
                    attachment.setDocType(docType);
                    attachmentRepository.save(attachment);

                }
            } else {
                return "{error: Permission Denied }";
            }
        } catch (Exception e) {
            return "{error: " + e.getMessage() + "}";
        }
        return "{}";
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

        }
    }


}
