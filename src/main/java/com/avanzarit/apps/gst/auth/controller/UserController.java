package com.avanzarit.apps.gst.auth.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;
import com.avanzarit.apps.gst.auth.db.model.Role;
import com.avanzarit.apps.gst.auth.db.model.DbUser;
import com.avanzarit.apps.gst.auth.ldap.model.LdapUser;
import com.avanzarit.apps.gst.auth.model.AppUser;
import com.avanzarit.apps.gst.auth.model.LoginForm;
import com.avanzarit.apps.gst.auth.service.SecurityService;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.email.CustomerMailProperties;
import com.avanzarit.apps.gst.email.EmailService;
import com.avanzarit.apps.gst.email.MAIL_SENDER;
import com.avanzarit.apps.gst.email.VendorMailProperties;
import com.avanzarit.apps.gst.model.workflow.WORKFLOW_GROUP;
import com.avanzarit.apps.gst.repository.WorkflowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
public class UserController implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Qualifier("dbUserService")
    @Autowired
    private UserService<DbUser> dbUserService;
    @Qualifier("ldapUserService")
    @Autowired
    private UserService<LdapUser> ldapUserService;
    @Qualifier("dbSecurityService")
    @Autowired
    private SecurityService<DbUser> dbSecurityService;
    @Qualifier("ldapSecurityService")
    @Autowired
    private SecurityService<LdapUser> ldapSecurityService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerMailProperties customerMailProperties;
    @Autowired
    private VendorMailProperties vendorMailProperties;
    @Autowired
    private WorkflowRepository workflowRepository;
    private ApplicationContext applicationContext;


    @Layout(value = "layouts/blank")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {
                model.addAttribute("error", "Your username and password is invalid.");
                session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("ADMIN")) {
            return "redirect:/adminLanding";
        } else if (roles.contains("BUSINESS_OWNER_VENDOR") || roles.contains("BUSINESS_OWNER_CUSTOMER")) {
            return "redirect:/businessOwnerLanding";
        } else if (roles.contains("VENDOR")) {
            return "redirect:/get";
        } else if (roles.contains("PO_ADMIN")||roles.contains("PO_ADMIN_MANAGER")||roles.contains("PO_FINANCE")||roles.contains("PO_FINANCE_MANAGER")||roles.contains("PO_ORG")||
                roles.contains("PO_ORG_MANAGER")||roles.contains("PO_TAX")||roles.contains("PO_TAX_MANAGER")) {
            return "redirect:/mdm/myworklist";
        } else if (roles.contains("CUSTOMER")) {
            return "redirect:/get/customer";
        }
        return "/404";
    }

    @RequestMapping(value = {"/mdm"}, method = RequestMethod.GET)
    public String welcomeMdm(Model model) {

        return "redirect:/mdm/myworklist";
    }

    /**
     * Called when following happens
     * a) Redirected by the CustomFilter if the logged in user is new. A user logging in for the first time needs to change there default password on initial login.
     * b) On clicking the reset password link that is sent to the user when user click the button to reset his password, called from showChangePasswordPage() method
     *
     * @param model
     * @param appContext
     * @return
     */
    @RequestMapping(value = {"/updatePassword", "{context}/updatePassword"}, method = RequestMethod.GET)
    public String updatePassword(Model model, @PathVariable(name = "context", required = false) String appContext) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(userName);
        model.addAttribute("loginForm", loginForm);
        model.addAttribute("context", StringUtils.isEmpty(appContext) ? "" : "/" + appContext);
        return "updatePassword";
    }

    /**
     * Called when user submits the new password from the updatepassword screen.
     * The new password is updated for the user and the user is logged off.
     *
     * @param redirectAttributes
     * @param loginForm
     * @param appContext
     * @return
     */
    @RequestMapping(value = {"/updatePassword", "{context}/updatePassword"}, method = RequestMethod.POST)
    public String changePassword(RedirectAttributes redirectAttributes, @ModelAttribute("loginForm") LoginForm loginForm, @PathVariable(name = "context", required = false) String appContext) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (isLdapUser(auth.getUsername())) {
            ldapSecurityService.changePassword(auth, loginForm.getPassword());
            redirectAttributes.addFlashAttribute("message", "Please login with the new Password");
        } else if (isDbUSer(auth.getUsername())) {
            dbSecurityService.changePassword(auth, loginForm.getPassword());
            redirectAttributes.addFlashAttribute("message", "Please login with the new Password");
        } else {
            redirectAttributes.addFlashAttribute("message", "Sorry user " + auth.getUsername() + " not found in the system");
        }

        if (appContext == null) {
            return "redirect:/logout";
        }
        return "redirect:/" + appContext + "/logout";
    }

    /**
     * Opens the reset password screen on clicking the button to reset the password from the login screen
     *
     * @param model
     * @return
     */
    @Layout(value = "layouts/blank")
    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.GET)
    public String loadResetPasswordPage(Model model) {
        return "resetPassword";
    }

    /**
     * Called on clicking the link to reset the password.
     * The user on requesting to reset the password receives a link from the system that contains the security token.
     * The user on clicking the link arrives at this method
     *
     * @param redirectAttributes
     * @param id
     * @param token
     * @return
     */
    @Layout(value = "layouts/blank")
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(RedirectAttributes redirectAttributes, @RequestParam("id") String id, @RequestParam("token") String token) {
        String result = ldapSecurityService.validatePasswordResetToken(id, token);
        if (result != null) {
            redirectAttributes.addFlashAttribute("error", result);
            return "redirect:/login";
        }
AppUser appUser=getUserByUserId(id);
        User userDetails=new org.springframework.security.core.userdetails
                .User(appUser.getUserId(), "", Collections.singleton(new SimpleGrantedAuthority("ROLE_PASSWORD_CHANGE")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, Arrays.asList(
                new SimpleGrantedAuthority("ROLE_PASSWORD_CHANGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/updatePassword";
    }

    /**
     * Called from the reset password screen after user enters his email ID to request for a rest of password.
     *
     * @param request
     * @param redirectAttributes
     * @param userEmail
     * @return
     * @throws MalformedURLException
     */
    @RequestMapping(value = "/resetPassword",
            method = RequestMethod.POST)
    public String resetPassword(HttpServletRequest request, RedirectAttributes redirectAttributes,
                                @RequestParam("email") String userEmail) throws MalformedURLException {
        String contextPath = getContextPath(request);
        AppUser appUser=getUserIdByEmail(userEmail);

        if (appUser == null) {

            redirectAttributes.addFlashAttribute("error", "User with this e-mail ID does not exist, please enter a valid e-mail ID");
            return "redirect:/resetPassword";
        }
        List<String> roles = appUser.getRolesAsString();

        String token = UUID.randomUUID().toString();
        if(appUser.getAuthSystem()== AUTH_SYSTEM.DB) {
            dbUserService.createPasswordResetTokenForUser(appUser.getUserId(), token);
        }else{
            ldapUserService.createPasswordResetTokenForUser(appUser.getUserId(),token);
        }

        MAIL_SENDER mailSender = getMailSender(roles);
        String fromMailId = "";
        SimpleMailMessage mailTemplate = null;
        if (mailSender == MAIL_SENDER.CUSTOMER) {
            fromMailId = customerMailProperties.getFromMailId();
            mailTemplate = (SimpleMailMessage) applicationContext.getBean("resetTokenMessage", customerMailProperties.isFromMailIdDifferent());

        } else if (mailSender == MAIL_SENDER.VENDOR) {
            fromMailId = vendorMailProperties.getFromMailId();
            mailTemplate = (SimpleMailMessage) applicationContext.getBean("resetTokenMessage", vendorMailProperties.isFromMailIdDifferent());
        }
        if (mailTemplate != null) {
            try {
                emailService.sendSimpleMessageUsingTemplate(appUser.getEmail(), "Reset Password", mailSender,
                        mailTemplate, contextPath, appUser.getUsername(), token, fromMailId);
                redirectAttributes.addFlashAttribute("message", "We have sent you a mail on your registered E-mail ID with a link to reset your password");
            } catch (Exception exception) {
                redirectAttributes.addFlashAttribute("error", "WARNING: Failed to trigger reset password Email. Please contact support.");
            }
        } else {
            LOGGER.error("Could not send email fail to retrieve email Template 'resetTokenMessage'");
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/useraction", method = RequestMethod.POST)
    public String sendReminderEmail(HttpServletRequest request, RedirectAttributes redirectAttributes,
                                    @RequestParam("email") String userEmail,
                                    @RequestParam("action") String action) throws MalformedURLException {
        String contextPath = getContextPath(request);
        AppUser appUser=getUserIdByEmail(userEmail);
        List<String> roles = appUser.getRolesAsString();
        if (appUser != null && action.equals("SEND_REMINDER_EMAIL")) {
            MAIL_SENDER mailSender = getMailSender(roles);
            String fromMailId = "";
            String mailSubject = "";
            SimpleMailMessage mailTemplate = null;
            if (mailSender == MAIL_SENDER.CUSTOMER) {
                fromMailId = customerMailProperties.getFromMailId();
                mailSubject = customerMailProperties.getLoginReminderSubject();
                mailTemplate = (SimpleMailMessage) applicationContext.getBean("loginReminderMessage", customerMailProperties.isFromMailIdDifferent(), MAIL_SENDER.CUSTOMER);
            } else if (mailSender == MAIL_SENDER.VENDOR) {
                fromMailId = vendorMailProperties.getFromMailId();
                mailTemplate = (SimpleMailMessage) applicationContext.getBean("loginReminderMessage", vendorMailProperties.isFromMailIdDifferent(), MAIL_SENDER.VENDOR);
                mailSubject = vendorMailProperties.getLoginReminderSubject();
            }
            try {
                if (mailTemplate != null) {
                    emailService.sendSimpleMessageUsingTemplate(appUser.getEmail(), mailSubject, mailSender, mailTemplate, contextPath, fromMailId);
                    redirectAttributes.addFlashAttribute("message", "Successfully sent a Login reminder mail to the vendor's registered E-mail ID");
                } else {
                    LOGGER.error("Could not send email fail to retrieve email Template 'resetTokenMessage'");
                }
            } catch (Exception exception) {
                redirectAttributes.addFlashAttribute("error", "Failed to trigger reminder Email");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to trigger reminder Email");
        }

        return "redirect:/userListView";
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("403");
        return model;

    }

    // for 404 page not found
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView pageNotFound() {
        ModelAndView model = new ModelAndView();
        model.setViewName("404");
        return model;
    }


    private MAIL_SENDER getMailSender(List<String> roles) {
        MAIL_SENDER mailSender = MAIL_SENDER.VENDOR;
        if (roles.contains("VENDOR")) {
            mailSender = MAIL_SENDER.VENDOR;
        } else if (roles.contains("CUSTOMER")) {
            mailSender = MAIL_SENDER.CUSTOMER;
        }
        return mailSender;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    private String getContextPath(HttpServletRequest request) throws MalformedURLException {
        String context = "";
        URL url = new URL(request.getRequestURL().toString());
        String host = url.getHost();
        String scheme = url.getProtocol();
        int port = url.getPort();

        if (port == 80 || port == 0) {
            context = scheme + "://" + host;
        } else {
            context = scheme + "://" + host + ":" + port;
        }
        return context;
    }

    private boolean isLdapUser(String userId) {
        return ldapUserService.findByUsername(userId) != null;
    }

    private boolean isDbUSer(String userId) {
        return dbUserService.findByUsername(userId) != null;
    }

    private AppUser getUserIdByEmail(String email){
        LdapUser ldapUser=ldapUserService.findByEmail(email);
        if(ldapUser!=null){
            return ldapUser;
        }

        DbUser dbUser=dbUserService.findByEmail(email);
        if(dbUser!=null){
            return dbUser;
        }

        return null;
    }

    private AppUser getUserByUserId(String userId){
        LdapUser ldapUser=ldapUserService.findByUsername(userId);
        if(ldapUser!=null){
            return ldapUser;
        }

        DbUser dbUser=dbUserService.findByUsername(userId);
        if(dbUser!=null){
            return dbUser;
        }

        return null;
    }
}
