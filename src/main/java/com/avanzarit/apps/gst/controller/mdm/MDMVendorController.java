package com.avanzarit.apps.gst.controller.mdm;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.auth.SYSTEM_ROLES;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.mdm.VENDOR_MGMT_ROLE;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_GROUP;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_STATUS;
import com.avanzarit.apps.gst.model.workflow.Workflow;
import com.avanzarit.apps.gst.repository.VendorRepository;
import com.avanzarit.apps.gst.repository.WorkflowRepository;
import com.avanzarit.apps.gst.service.VendorManagementService;
import com.avanzarit.apps.gst.service.WorkflowManagementService;
import com.avanzarit.apps.gst.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/mdm")
class MDMVendorController {
    private static final Logger logger = LoggerFactory.getLogger(MDMVendorController.class);

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    WorkflowRepository workflowRepository;

    @Autowired
    VendorManagementService vendorManagementService;

    @Autowired
    WorkflowManagementService workflowManagementService;

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/claim/{workflowItem}", method = RequestMethod.POST)
    public String claimWorkflowItem(RedirectAttributes redirectAttributes, @PathVariable String workflowItem, Model model) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        Workflow workflow = workflowRepository.findByChangeRequestId(Long.valueOf(workflowItem));
        if (StringUtils.isEmpty(workflow.getAssignedUser())) {
            if (workflow.getAssignedGroup() == SYSTEM_ROLES.valueOf(roles.toArray(new String[0])[0])) {
                workflow.setAssignedUser(auth.getUsername());
                workflowRepository.save(workflow);
                List<Workflow> workflowList = workflowRepository.findByAssignedGroup(SYSTEM_ROLES.valueOf((String) roles.toArray()[0]));
                redirectAttributes.addFlashAttribute("items", workflowList);
                redirectAttributes.addFlashAttribute("context", "mdm");
                redirectAttributes.addFlashAttribute("message", "Successfully Claimed Work Item number " + workflow.getChangeRequestId());
            } else {
                redirectAttributes.addFlashAttribute("message", "Cannot claim a workItem that does not belong to the group you belong to");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Cannot claim an already Claimed Work Item");
        }


        return "redirect:/mdm/myworklist";
    }

    @Layout(value = "layouts/mdmdefault")
    @RequestMapping(path = "/reassign/{workflowItem}", method = RequestMethod.GET)
    public String reassignWorkflowItem(RedirectAttributes redirectAttributes, @PathVariable String workflowItem) {

        return "redirect:/404";
    }

    @Layout(value = "layouts/mdmCreateVendor")
    @RequestMapping(path = "/myworklist", method = RequestMethod.GET)
    public String showMyWorkListScreen(RedirectAttributes redirectAttributes, Model model) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        List<Workflow> workflowList = workflowRepository.findByAssignedGroup(SYSTEM_ROLES.valueOf((String) roles.toArray()[0]));
        model.addAttribute("items", workflowList);
        model.addAttribute("context", "mdm");
        return "mdm/myWorkList";
    }


    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/createNewVendor", method = RequestMethod.POST)
    public String createNewVendor(RedirectAttributes redirectAttributes, Model model, @RequestParam String vendorName) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Vendor vendor = new Vendor();

        vendor.setVendorName1(vendorName);
        workflowManagementService.createWorkflowItemsFor(vendor, WORKFLOW_GROUP.PO_WORKFLOW);
        List<Workflow> workflow = workflowRepository.findByVendor(vendor);
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("model", vendor);
        model.addAttribute("context", "mdm");
        model.addAttribute("workflow", workflow);
        model.addAttribute("landingpage", "vendorFormGeneral");
        return "mdm/vendorFormGeneral";
    }


    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute Vendor vendor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        Vendor vendorFromDatabase = vendorRepository.findByVendorId(vendor.getVendorId());
        Long workflowId = vendor.getWorkflowId();
        Workflow workflow = workflowRepository.findByChangeRequestId(workflowId);
        copyOverProperties(vendorFromDatabase, vendor, workflow.getAssignedGroup());
        vendorManagementService.save(vendorFromDatabase, userName);

        redirectAttributes.addFlashAttribute("model", vendor);
        logger.debug("Forwarding to the exportVendorJob list...");
        if (vendor.getSubmityn() != null && vendor.getSubmityn().equals("N")) {
            redirectAttributes.addFlashAttribute("message", "Your changes have been Saved!");
            return "redirect:/mdm/get/" + workflowId.longValue() + "/" + vendor.getVendorId();
        } else {
            WORKFLOW_STATUS nextStatus = workflow.getStatus().getNextWorkflowStatus();
            SYSTEM_ROLES targetSystemRole = workflow.getType().getGroupToAssign(nextStatus);
            workflow.setAssignedUser(null);
            workflow.setAssignedGroup(targetSystemRole);
            workflow.setStatus(nextStatus);
            workflowRepository.save(workflow);

            redirectAttributes.addFlashAttribute("message", "Successfully Submitted the changes");
        }

        return "redirect:/mdm/get/" + workflowId.longValue() + "/" + vendor.getVendorId();
    }

    private void copyOverProperties(Vendor newVendor, Vendor oldVendor, SYSTEM_ROLES workFlowForGroup) {

        List<Field> fields = Utils.findFieldsForTabs(Vendor.class, getTabForWorkflowType(workFlowForGroup));
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

    private List<String> getTabForWorkflowType(SYSTEM_ROLES workFlowForGroup) {
        if (workFlowForGroup == SYSTEM_ROLES.PO_ADMIN || workFlowForGroup == SYSTEM_ROLES.PO_ADMIN_MANAGER) {
            return Arrays.asList("Basic", "Communication", "Address", "ContactPerson", "Material", "Service");
        } else if (workFlowForGroup == SYSTEM_ROLES.PO_FINANCE || workFlowForGroup == SYSTEM_ROLES.PO_FINANCE_MANAGER) {
            return Arrays.asList("Finance");
        } else if (workFlowForGroup == SYSTEM_ROLES.PO_TAX || workFlowForGroup == SYSTEM_ROLES.PO_TAX_MANAGER) {
            return Arrays.asList("Tax");
        } else {
            return Arrays.asList("PoOrg");
        }
    }

    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(path = "/get/{workflowId}/{vendorId}", method = RequestMethod.GET)
    public String showVendor(RedirectAttributes redirectAttributes, Model model, @PathVariable String workflowId, @PathVariable String vendorId) {

        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth == null) {
            return "redirect:/login";
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (VENDOR_MGMT_ROLE.PO_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
            Workflow currentWorkflow = workflowRepository.findByChangeRequestId(Long.valueOf(workflowId));
            if (currentWorkflow.getAssignedGroup() == null && currentWorkflow.getStatus() == WORKFLOW_STATUS.COMPLETE) {
                return "redirect:/mdm/myworklist";
            }
            Vendor vendor = vendorRepository.findByVendorId(vendorId);
            List<Workflow> workflow = workflowRepository.findByVendor(vendor);

            if (vendor == null) {
                redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
                return "redirect:/404";
            } else {
                vendor.setWorkflowId(currentWorkflow.getChangeRequestId());
                String landingPage = getLandingPage(currentWorkflow);
                if (landingPage != null) {
                    vendor.setSubmityn(isPageReadOnly(auth, roles, vendor, currentWorkflow));
                    model.addAttribute("model", vendor);
                    model.addAttribute("workflow", workflow);
                    model.addAttribute("context", "mdm");
                    model.addAttribute("landingpage", landingPage);
                    return "mdm/" + landingPage;
                }
            }
        }

        return "redirect:/403";
    }

    @Layout(value = "layouts/mdmVendorForm")
    @RequestMapping(value = {"/{landingPage}/{workflowId}/{vendorId}"}, method = RequestMethod.GET)
    public String showVendorBasicDetails(RedirectAttributes redirectAttributes, Model model,
                                         @PathVariable String landingPage, @PathVariable String workflowId,
                                         @PathVariable String vendorId) {

        if (isValidLandingPagePathVariable(landingPage)) {
            String formName = getFormName(landingPage);
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (auth == null) {
                return "redirect:/login";
            }
            Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
            if (VENDOR_MGMT_ROLE.PO_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
                Vendor vendor = vendorRepository.findByVendorId(vendorId);
                if (vendor == null) {
                    redirectAttributes.addFlashAttribute("message", "Vendor Data not available, please contact for more Details");
                    return "redirect:/404";
                } else {
                    List<Workflow> workflow = workflowRepository.findByVendor(vendor);
                    Workflow currentWorkflow = workflowRepository.findByChangeRequestId(Long.valueOf(workflowId));
                    vendor.setWorkflowId(Long.valueOf(workflowId));
                    vendor.setSubmityn(isPageReadOnly(auth, roles, vendor, currentWorkflow, formName));
                    model.addAttribute("model", vendor);
                    model.addAttribute("context", "mdm");
                    model.addAttribute("workflow", workflow);
                    model.addAttribute("landingpage", formName);
                    return "mdm/" + formName;
                }
            }
        }

        return "redirect:/404";
    }

    private boolean isValidLandingPagePathVariable(String landingPage) {
        if (landingPage.equals("poadminlanding") || landingPage.equals("pofinancelanding")
                || landingPage.equals("potaxlanding") || landingPage.equals("poorglanding")) {
            return true;
        }

        return false;
    }

    private String getFormName(String landingPage) {
        switch (landingPage) {
            case "poadminlanding":
                return "vendorFormGeneral";
            case "pofinancelanding":
                return "vendorFormFinance";
            case "potaxlanding":
                return "vendorFormTax";
            case "poorglanding":
                return "vendorFormOrg";
        }

        return null;
    }

    private String getLandingPage(Workflow workflow) {
        SYSTEM_ROLES workflowForRole = workflow.getAssignedGroup();
        if (workflowForRole == SYSTEM_ROLES.PO_ADMIN || workflowForRole == SYSTEM_ROLES.PO_ADMIN_MANAGER) {
            return "vendorFormGeneral";
        } else if (workflowForRole == SYSTEM_ROLES.PO_FINANCE || workflowForRole == SYSTEM_ROLES.PO_FINANCE_MANAGER) {
            return "vendorFormFinance";
        } else if (workflowForRole == SYSTEM_ROLES.PO_TAX || workflowForRole == SYSTEM_ROLES.PO_TAX_MANAGER) {
            return "vendorFormTax";
        } else if (workflowForRole == SYSTEM_ROLES.PO_ORG || workflowForRole == SYSTEM_ROLES.PO_ORG_MANAGER) {
            return "vendorFormOrg";
        }

        return null;
    }

    private boolean isPageReadOnly(UserDetails auth, Set<String> roles, Vendor vendor, Workflow workflow) {
        SYSTEM_ROLES role = SYSTEM_ROLES.valueOf(roles.toArray(new String[0])[0]);
        return workflow.getAssignedGroup() != role || workflow.getAssignedUser() == null || !workflow.getAssignedUser().equals(auth.getUsername());
    }

    private boolean isPageReadOnly(UserDetails auth, Set<String> roles, Vendor vendor, Workflow workflow, String landingPage) {
        SYSTEM_ROLES role = SYSTEM_ROLES.valueOf(roles.toArray(new String[0])[0]);
        switch (landingPage) {
            case "vendorFormGeneral":
                if (VENDOR_MGMT_ROLE.PO_ADMIN_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
                    return workflow.getAssignedGroup() != role || workflow.getAssignedUser() == null || !workflow.getAssignedUser().equals(auth.getUsername());
                }
                return true;
            case "vendorFormFinance":
                if (VENDOR_MGMT_ROLE.PO_FINANCE_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
                    return workflow.getAssignedGroup() != role || workflow.getAssignedUser() == null || !workflow.getAssignedUser().equals(auth.getUsername());
                }
                return true;
            case "vendorFormTax":
                if (VENDOR_MGMT_ROLE.PO_TAX_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
                    return workflow.getAssignedGroup() != role || workflow.getAssignedUser() == null || !workflow.getAssignedUser().equals(auth.getUsername());
                }
                return true;
            case "vendorFormOrg":
                if (VENDOR_MGMT_ROLE.PO_ORG_ROLES.getSystemRoles().stream().anyMatch(roles::contains)) {
                    return workflow.getAssignedGroup() != role || workflow.getAssignedUser() == null || !workflow.getAssignedUser().equals(auth.getUsername());
                }
                return true;
        }

        return true;
    }
}
