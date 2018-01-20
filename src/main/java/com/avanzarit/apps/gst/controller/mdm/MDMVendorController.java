package com.avanzarit.apps.gst.controller.mdm;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.auth.model.Role;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.model.Attachment;
import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.ServiceSacMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.VendorStatusEnum;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mdm")
class MDMVendorController {

    private static final Logger logger = LoggerFactory.getLogger(MDMVendorController.class);

    @Autowired
    private HsnMasterRepository hsnMasterRepository;

    @Autowired
    private SacMasterRepository sacMasterRepository;

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
    VendorRepository vendorRepository;

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    VendorManagementService vendorManagementService;

    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Vendor vendor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();

        vendorManagementService.save(vendor, userName);

        redirectAttributes.addFlashAttribute("model", vendor);
        logger.debug("Forwarding to the exportVendorJob list...");
        if (vendor.getSubmityn() != null && vendor.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes have been Saved!");
            return "redirect:/mdm/get/"+vendor.getVendorId();
        }
        redirectAttributes.addFlashAttribute("message",
                "Successfully Submitted the changes");
        return "redirect:/mdm/get/"+vendor.getVendorId();
    }


    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/get/{vendorId}", method = RequestMethod.GET)
    public String showVendor(RedirectAttributes redirectAttributes, Model model, @PathVariable String vendorId) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        Vendor vendor = vendorRepository.findByVendorId(vendorId);
        if (vendor == null) {
            redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
            return "redirect:/404";
        } else {
            Workflow workflow = workflowRepository.findByVendor(vendor);

            model.addAttribute("model", vendor);
            model.addAttribute("context", "mdm");
            if (workflow.getStage().equals("GENERAL")) {
                model.addAttribute("stage", "GENERAL");
                return "mdm/vendorFormGeneral";
            }
        }

        return "redirect:/403";
    }

    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/createNewVendor", method = RequestMethod.POST)
    public String createNewVendor(RedirectAttributes redirectAttributes, Model model, @RequestParam String vendorName) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        Workflow workflow = new Workflow();
        workflow = workflowRepository.save(workflow);
        workflow.setAssignedGroup((String) roles.toArray()[0]);
        workflow.setStage("GENERAL");
        workflow.setStatus("DRAFT");
        Vendor vendor = new Vendor();
        vendor.setVendorId(workflow.getChangeRequestId().toString());
        vendor.setVendorName1(vendorName);
        vendor = vendorRepository.save(vendor);
        workflow.setVendor(vendor);
        workflowRepository.save(workflow);
        model.addAttribute("model", vendor);
        model.addAttribute("context", "mdm");
        model.addAttribute("stage", "GENERAL");
        return "mdm/vendorFormGeneral";
    }


    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/myworklist", method = RequestMethod.GET)
    public String showMyWorkListScreen(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        List<Workflow> workflowList = workflowRepository.findByAssignedGroup((String) roles.toArray()[0]);
        List<Vendor> vendors = workflowList.stream().map(Workflow::getVendor).collect(Collectors.toList());
        model.addAttribute("vendors", vendors);
        model.addAttribute("context", "mdm");
        return "mdm/myWorkList";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/adminlanding", method = RequestMethod.GET)
    public String showAdminScreen(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        return "mdm/vendorFormGeneral";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/poadminlanding", method = RequestMethod.GET)
    public String showVendorBasicDetails(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        return "mdm/vendorFormGeneral";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/pofinancelanding", method = RequestMethod.GET)
    public String showFinanceDetails(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        return "mdm/vendorFormFinance";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/potaxlanding", method = RequestMethod.GET)
    public String showTaxDetails(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        return "mdm/vendorFormTax";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/poorglanding", method = RequestMethod.GET)
    public String showPoOrgDetails(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        return "mdm/vendorFormPoOrg";
    }
}
