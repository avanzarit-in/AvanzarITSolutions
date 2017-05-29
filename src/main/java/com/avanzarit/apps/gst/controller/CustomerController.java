package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.repository.CustomerRepository;
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

/**
 * Created by SPADHI on 5/3/2017.
 */
@Controller
@RequestMapping("/customer")
class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerRepository customerRepository;


    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String showAddCustomer(Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "customerForm";
    }

    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public String showCustomer(Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        String userName = auth.getUsername();
        Customer customer = customerRepository.findByCustomerId(userName);
        if (customer == null) {
            model.addAttribute("customer", new Customer());
        } else {
            model.addAttribute("customer", customer);
        }
        return "customerForm";
    }

    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        customer = customerRepository.save(customer);
        redirectAttributes.addFlashAttribute("customer", customer);

        if (customer.getSubmityn() != null && customer.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes hav been Saved!");
            return "redirect:add";
        }
        redirectAttributes.addFlashAttribute("message",
                "Successfully Submitted the changes");
        return "redirect:add";
    }

    @RequestMapping(path = "/customerListView", method = RequestMethod.GET)
    public ModelAndView showAlbumsList() {
        logger.debug("in showCustomerList");

        ModelAndView mav = new ModelAndView();
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        Collection<? extends GrantedAuthority> groups = auth.getAuthorities();
        for (GrantedAuthority authority : groups) {
            String authGroup = authority.getAuthority();
        }

        if (userName.equals("admin")) {
            mav.addObject("customers", customerRepository.findAll());
        } else {
            mav.addObject("customers", customerRepository.findByCustomerId(userName));
        }
        mav.setViewName("customerListView");
        return mav;
    }
}
