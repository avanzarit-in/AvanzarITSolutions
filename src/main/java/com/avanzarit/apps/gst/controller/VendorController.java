package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.model.ContactPersonMaster;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.ContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

/**
 * Created by SPADHI on 5/3/2017.
 */
@Controller
class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);


    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MaterialMasterRepository materialMasterRepository;

    @Autowired
    private ContactPersonMasterRepository contactPersonMasterRepository;


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

    @RequestMapping(path = "/reset", method = RequestMethod.POST)
    public String resetVendorStatus(RedirectAttributes redirectAttributes, @RequestParam("resetVendorId") String vendorId) {

        Vendor vendor = vendorRepository.findByVendorId(vendorId);
        if (vendor != null) {
            vendor.setSubmityn("N");
            vendorRepository.save(vendor);
            redirectAttributes.addFlashAttribute("message", "Vendor Data Reset Successful");

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

        List<MaterialMaster> materials = vendor.getMaterialMaster();
        List<ContactPersonMaster> contactPersonMasters = vendor.getContactPersonMaster();
        for (MaterialMaster material : materials) {
            material.setVendor(vendor);
        }
        for (ContactPersonMaster contactPersonMaster : contactPersonMasters) {
            contactPersonMaster.setVendor(vendor);
        }
        vendor = vendorRepository.save(vendor);

        List<MaterialMaster> materialList = materialMasterRepository.findByVendor(vendor);
        for (MaterialMaster material : materialList) {
            if (!materials.contains(material)) {
                materialMasterRepository.delete(material);
            }
        }

        List<ContactPersonMaster> contactPersonMasterList = contactPersonMasterRepository.findByVendor(vendor);
        for (ContactPersonMaster contactPersonMaster : contactPersonMasterList) {
            if (!contactPersonMasters.contains(contactPersonMaster)) {
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

    @RequestMapping(path = "/vendorListView", method = RequestMethod.GET)
    public ModelAndView showVendorList() {
        logger.debug("in showVendorList");

        ModelAndView mav = new ModelAndView();
        mav.addObject("vendors", vendorRepository.findAll());
        mav.setViewName("vendorListView");
        return mav;
    }
}
