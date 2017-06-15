package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.annotations.CopyOver;
import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import com.avanzarit.apps.gst.model.CustomerStatusEnum;
import com.avanzarit.apps.gst.repository.CustomerContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.CustomerRepository;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerContactPersonMasterRepository customerContactPersonMasterRepository;

    @Autowired
    StorageService storageService;


    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/get/customer", method = RequestMethod.GET)
    public String showVendor(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        String userName = auth.getUsername();
        Customer customer = customerRepository.findByCustomerId(userName);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("message", "Customer Data not available, please contact for more Details");
            return "redirect:/404";
        } else {
            model.addAttribute("model", customer);
        }
        return "customerForm";
    }

    @RequestMapping(path = "/action/customer", method = RequestMethod.POST)
    public String resetVendorStatus(RedirectAttributes redirectAttributes,
                                    @RequestParam("customerId") String customerId,
                                    @RequestParam("action") String action) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer != null) {
            if (action.equalsIgnoreCase("APPROVE")) {
                customer.setSubmityn("Y");
                customer.setApprovedBy(userName);
                customer.setLastApprovedOn(new Date());
                customer.setCustomerStatus(CustomerStatusEnum.APPROVED);
                customerRepository.save(customer);
                redirectAttributes.addFlashAttribute("message", "Customer Data Approved Successfully");

            } else if (action.equalsIgnoreCase("REJECT")) {
                customer.setSubmityn("N");
                customer.setRejectedBy(userName);
                customer.setLastRejectedOn(new Date());
                customer.setCustomerStatus(CustomerStatusEnum.REJECTED);
                customerRepository.save(customer);
                redirectAttributes.addFlashAttribute("message", "Customer Data Rejected Successfully");

            } else if (action.equalsIgnoreCase("RESET")) {
                customer.setSubmityn("N");
                customer.setLastRevertedBy(userName);
                customer.setLastRevertedOn(new Date());
                customer.setRevertCount(customer.getRevertCount() + 1);
                customer.setRevertReason("Manual Action");
                customer.setCustomerStatus(CustomerStatusEnum.REVERTED);
                customerRepository.save(customer);
                redirectAttributes.addFlashAttribute("message", "Customer Data Reset Successfully");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Customer Data could not be Reset contact support");
        }
        return "redirect:/customerListView";
    }

    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/get/customer/{customerId}", method = RequestMethod.GET)
    public String showCUstomer(RedirectAttributes redirectAttributes, Model model, @PathVariable String customerId) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("BUSINESS_OWNER") || (roles.contains("CUSTOMER") && auth.getUsername().equals(customerId))) {
            Customer customer = customerRepository.findByCustomerId(customerId);
            if (customer == null) {
                redirectAttributes.addFlashAttribute("message", "CUstomer Data not available, please contact for more Details");
                return "redirect:/404";
            } else {
                if (roles.contains("BUSINESS_OWNER")) {
                    customer.setSubmityn("Y");
                }
                model.addAttribute("model", customer);
            }
            return "customerForm";
        }
        return "redirect:/403";
    }

    @Layout(value = "layouts/customerForm")
    @RequestMapping(path = "/add/customer", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();

        List<CustomerContactPersonMaster> customerContactPersonMasters = customer.getCustomerContactPersonMaster();
        List<CustomerContactPersonMaster> cleanedCustomerContactPersonMaster = customerContactPersonMasters.stream()
                .filter(line -> line.getId() != null)
                .collect(Collectors.toList());


        for (CustomerContactPersonMaster customerContactPersonMaster : cleanedCustomerContactPersonMaster) {
            if (customerContactPersonMaster.getId().equals(999L)) {
                customerContactPersonMaster.setId(null);
            }
            customerContactPersonMaster.setCustomer(customer);
            customerContactPersonMasterRepository.save(customerContactPersonMaster);
        }

        if (customer.getSubmityn().equalsIgnoreCase("Y")) {
            customer.setSubmittedBy(userName);
            customer.setLastSubmittedOn(new Date());
        } else {
            customer.setModifiedBy(userName);
            customer.setLastModifiedOn(new Date());
        }

        Customer oldCustomer = customerRepository.findByCustomerId(customer.getCustomerId());
        copyOverProperties(customer, oldCustomer);
        if (customer.getSubmityn().equalsIgnoreCase("Y")) {
            customer.setSubmittedBy(userName);
            customer.setLastSubmittedOn(new Date());
            customer.setCustomerStatus(CustomerStatusEnum.SUBMITTED);
        } else {
            customer.setModifiedBy(userName);
            customer.setLastModifiedOn(new Date());
            customer.setCustomerStatus(CustomerStatusEnum.MODIFIED);
        }

        customerRepository.save(customer);

        List<CustomerContactPersonMaster> customerContactPersonMasterList = customerContactPersonMasterRepository.findByCustomer(customer);
        for (CustomerContactPersonMaster customerContactPersonMaster : customerContactPersonMasterList) {
            if (!cleanedCustomerContactPersonMaster.contains(customerContactPersonMaster)) {
                customerContactPersonMasterRepository.delete(customerContactPersonMaster);
            }
        }

        User user = userRepository.findByUsername(customer.getCustomerId());
        if (user != null) {
            user.setEmail(customer.getEmail());
            user.setTelephone(customer.getTelephoneNumberExtn());
            user.setMobile(customer.getMobileNo());
            userRepository.save(user);
        }

        redirectAttributes.addFlashAttribute("model", customer);
        logger.debug("Forwarding to the exportCustomerJob list...");
        if (customer.getSubmityn() != null && customer.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes have been Saved!");
            return "redirect:/get/customer";
        }
        redirectAttributes.addFlashAttribute("message",
                "Successfully Submitted the changes");
        return "redirect:/get/customer";
    }

    @RequestMapping(path = "/customerListView", method = RequestMethod.GET)
    public ModelAndView showCustomerList() {
        logger.debug("in showCustomerList");

        ModelAndView mav = new ModelAndView();
        mav.addObject("customers", customerRepository.findAll());
        mav.setViewName("customerListView");
        return mav;
    }

    private void copyOverProperties(Customer newCustomer, Customer oldCustomer) {
        List<Field> fields = Utils.findFields(Customer.class, CopyOver.class);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(oldCustomer);
                field.set(newCustomer, value);
            }
        } catch (Exception exception) {

        }
    }
}
