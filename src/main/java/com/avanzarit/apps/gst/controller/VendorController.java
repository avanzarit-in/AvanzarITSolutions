package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.auth.db.model.Role;
import com.avanzarit.apps.gst.auth.db.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.db.repository.UserRepository;
import com.avanzarit.apps.gst.model.Attachment;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.VendorStatusEnum;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_GROUP;
import com.avanzarit.apps.gst.model.workflow.Workflow;
import com.avanzarit.apps.gst.repository.AttachmentRepository;
import com.avanzarit.apps.gst.repository.ContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.HsnMasterRepository;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.SacMasterRepository;
import com.avanzarit.apps.gst.repository.ServiceSacMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import com.avanzarit.apps.gst.repository.WorkflowRepository;
import com.avanzarit.apps.gst.service.VendorManagementService;
import com.avanzarit.apps.gst.service.WorkflowManagementService;
import com.avanzarit.apps.gst.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private WorkflowRepository workflowRepository;
    @Autowired
    private WorkflowManagementService workflowManagementService;
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

    @Autowired
    VendorManagementService vendorManagementService;


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
            model.addAttribute("model", vendor);
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
                List<Workflow> workflowList=workflowRepository.findByVendor(vendor);
                if(workflowList==null || workflowList.isEmpty()) {
                    vendor.setSubmityn("N");
                    vendor.setRejectedBy(userName);
                    vendor.setLastRejectedOn(new Date());
                    vendor.setVendorStatus(VendorStatusEnum.REJECTED);
                    vendorRepository.save(vendor);
                    redirectAttributes.addFlashAttribute("message", "Vendor Data Rejected Successfully");
                }else{
                    redirectAttributes.addFlashAttribute("error", "Could not Reject Vendor Data as it is already in a approval workflow");
                }

            } else if (action.equalsIgnoreCase("RESET")) {
                vendor.setSubmityn("N");
                vendor.setLastRevertedBy(userName);
                vendor.setLastRevertedOn(new Date());
                vendor.setRevertCount(vendor.getRevertCount() + 1);
                vendor.setRevertReason("Manual Action");
                vendor.setVendorStatus(VendorStatusEnum.REVERTED);
                vendorRepository.save(vendor);
                redirectAttributes.addFlashAttribute("message", "Vendor Data Reset Successfully");
            }else if (action.equalsIgnoreCase("VALIDATE")) {
                List<Workflow> workflowList=workflowRepository.findByVendor(vendor);
                if(workflowList==null || workflowList.isEmpty()) {
                    vendor.setSubmityn("Y");
                    vendor.setLastSubmittedForApprovalBy(userName);
                    vendor.setLastSubmittedForApprovalOn(new Date());
                    vendor.setVendorStatus(VendorStatusEnum.SUBMITTED_APPROVAL);
                    vendorRepository.save(vendor);
                    workflowManagementService.createWorkflowItemsFor(vendor, WORKFLOW_GROUP.PO_WORKFLOW);
                    redirectAttributes.addFlashAttribute("message", "Vendor Data Successfully Submitted for Approval");
                }else{
                    redirectAttributes.addFlashAttribute("error", "Could not Submit Vendor Data for Approval as it is already in a approval workflow");
                }

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
        if (roles.contains("ADMIN") || roles.contains("BUSINESS_OWNER_VENDOR") || (roles.contains("VENDOR") && auth.getUsername().equals(vendorId))) {
            Vendor vendor = vendorRepository.findByVendorId(vendorId);
            if (vendor == null) {
                redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
                return "redirect:/404";
            } else {
                if (!roles.contains("VENDOR")) {
                    vendor.setSubmityn("Y");
                }
                model.addAttribute("model", vendor);
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

        vendorManagementService.save(vendor, userName);

        redirectAttributes.addFlashAttribute("model", vendor);
        logger.debug("Forwarding to the exportVendorJob list...");
        if (vendor.getSubmityn() != null && vendor.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes have been Saved!");
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
    public String loadBusinessOwnerLandingPage(Model model)

    {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("BUSINESS_OWNER_VENDOR")) {
            return "redirect:vendorListView";
        } else if (roles.contains("BUSINESS_OWNER_CUSTOMER")) {
            return "redirect:customerListView";
        }
        return "redirect:403";
    }

    @RequestMapping(path = "/userListView", method = RequestMethod.GET)
    public ModelAndView showUserList() {
        logger.debug("in showUserList");
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        ModelAndView mav = new ModelAndView();
        if (roles.contains("ADMIN")) {
            mav.addObject("users", userRepository.findAll());
        } else if (roles.contains("BUSINESS_OWNER_VENDOR")) {
            Role adminRole = roleRepository.findByName("VENDOR");
            mav.addObject("users", userRepository.findByRoles(Arrays.asList(adminRole)));
        } else if (roles.contains("BUSINESS_OWNER_CUSTOMER")) {
            Role adminRole = roleRepository.findByName("CUSTOMER");
            mav.addObject("users", userRepository.findByRoles(Arrays.asList(adminRole)));
        }
        mav.setViewName("userListView");
        return mav;
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


}
