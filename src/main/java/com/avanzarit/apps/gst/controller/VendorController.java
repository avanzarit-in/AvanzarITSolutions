package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

/**
 * Created by SPADHI on 5/3/2017.
 */
@Controller
class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);


    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private MaterialMasterRepository materialMasterRepository;


    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String showAddVendor(Model model) {
        logger.debug("in showAdd exportVendorJob");

        if (!model.containsAttribute("vendor")) {

            model.addAttribute("vendor", new Vendor());
        }
        //   model.addAttribute("vendor", new exportVendorJob());

        return "vendorForm";
    }

    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public String showVendor(Model model) {
        logger.debug("in show exportVendorJob");
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        String userName = auth.getUsername();
        Vendor vendor = vendorRepository.findByVendorId(userName);
        if (vendor == null) {
            model.addAttribute("vendor", new Vendor());
        } else {
            model.addAttribute("vendor", vendor);
        }
        return "vendorForm";
    }

    @Layout(value = "layouts/vendorForm")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Vendor vendor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.debug("in add exportVendorJob => " + vendor);

        List<MaterialMaster> materials = vendor.getMaterial();
        for (MaterialMaster material : materials) {
            material.setVendor(vendor);
        }
        vendor = vendorRepository.save(vendor);

        List<MaterialMaster> materialList = materialMasterRepository.findByVendor(vendor);
        for (MaterialMaster material : materialList) {
            if (!materials.contains(material)) {
                materialMasterRepository.delete(material);
            }
        }

        redirectAttributes.addFlashAttribute("vendor", vendor);
        logger.debug("Forwarding to the exportVendorJob list...");
        if (vendor.getSubmityn() != null && vendor.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes hav been Saved!");
            return "redirect:add";
        }
        redirectAttributes.addFlashAttribute("message",
                "Successfully Submitted the changes");
        return "redirect:add";
    }

    @RequestMapping(path = "/vendorListView", method = RequestMethod.GET)
    public ModelAndView showAlbumsList() {
        logger.debug("in showVendorList");

        ModelAndView mav = new ModelAndView();
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        Collection<? extends GrantedAuthority> groups=auth.getAuthorities();
        for(GrantedAuthority authority:groups){
            String authGroup=authority.getAuthority();
        }

        if (userName.equals("admin")) {
            mav.addObject("vendors", vendorRepository.findAll());
        } else {
            mav.addObject("vendors", vendorRepository.findByVendorId(userName));
        }
        mav.setViewName("vendorListView");
        return mav;
    }
}
