package com.avanzarit.apps.vendormgmt.controller;

import com.avanzarit.apps.vendormgmt.auth.validator.VendorValidator;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by SPADHI on 5/3/2017.
 */
@Controller
class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorValidator vendorValidator;

    @Autowired
    private VendorRepository vendorRepository;



    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String showAddVendor(Model model) {
        logger.debug("in showAdd Vendor");

        if (!model.containsAttribute("vendor")) {

            model.addAttribute("vendor", new Vendor());
        }
        model.addAttribute("vendor", new Vendor());

        return "vendorForm";
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public String showVendor(Model model) {
        logger.debug("in show Vendor");
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(auth==null){
            return "redirect:/login";
        }
        String userName = auth.getUsername();
        Vendor vendor = vendorRepository.findByName(userName);
        if(vendor==null){
            model.addAttribute("vendor", new Vendor());
        }else {
            model.addAttribute("vendor", vendor);
        }
        return "vendorForm";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Vendor vendor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.debug("in add Vendor => " + vendor);

        vendorValidator.validate(vendor, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.warn("There are validation errors");
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vendor", bindingResult);
            redirectAttributes.addFlashAttribute("vendor", vendor);
            return "redirect:/add";
        }

        vendorRepository.save(vendor);

        logger.debug("Forwarding to the albums list...");
        return "redirect:vendorListView";
    }

    @RequestMapping(path = "/vendorListView", method = RequestMethod.GET)
    public ModelAndView showAlbumsList() {
        logger.debug("in showVendorList");

        ModelAndView mav = new ModelAndView();
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        if(userName.equals("administrator")){
            mav.addObject("vendors", vendorRepository.findAll());
        }else{
            mav.addObject("vendors", vendorRepository.findByName(userName));
        }
        mav.setViewName("vendorListView");
        return mav;
    }
}
