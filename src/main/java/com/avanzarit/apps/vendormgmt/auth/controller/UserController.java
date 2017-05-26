package com.avanzarit.apps.vendormgmt.auth.controller;

import com.avanzarit.apps.vendormgmt.Layout;
import com.avanzarit.apps.vendormgmt.auth.exception.UserNotFoundException;
import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.auth.model.UserStatusEnum;
import com.avanzarit.apps.vendormgmt.auth.repository.UserRepository;
import com.avanzarit.apps.vendormgmt.auth.response.GenericResponse;
import com.avanzarit.apps.vendormgmt.auth.service.SecurityService;
import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import com.avanzarit.apps.vendormgmt.auth.validator.UserValidator;
import com.avanzarit.apps.vendormgmt.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailService emailService;

    @Autowired
    SimpleMailMessage resetTokenMessage;


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/add";
    }

    @Layout(value = "layouts/blank")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null)
                model.addAttribute("error", "Your username and password is invalid.");
        }
      /*  if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
*/
        return "login";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authCollection= auth.getAuthorities();

        String userName = auth.getUsername();
        if(userName.equals("admin")){
            return "redirect:/vendorListView";
        }else{
            return "redirect:/get";
        }

    }

    @RequestMapping(value = {"/updatePassword"}, method = RequestMethod.GET)
    public String updatePassword(Model model) {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        User user = new User();
        user.setUsername(userName);
        model.addAttribute("user", user);
        return "updatePassword";
    }

    @RequestMapping(value = {"/updatePassword"}, method = RequestMethod.POST)
    public String changePassword(@ModelAttribute("user") User user) {

        String password = user.getPassword();
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = auth.getUsername();
        User repUser = userRepository.findByUsername(userName);
        repUser.setLastLoginDate(new Date());
        repUser.setUserStatus(UserStatusEnum.ACTIVE);
        repUser.setPassword(password);
        userService.save(repUser);
        securityService.autologin(user.getUsername(), password);
        return "/logout";
    }

    @Layout(value = "layouts/blank")
    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.GET)
    public String loadResetPasswordPage(Model model) {

        return "resetPassword";
    }

    @RequestMapping(value = "/resetPassword",
            method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        String contextPath = request.getRequestURL().toString();
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        emailService.sendSimpleMessageUsingTemplate(user.getEmail(), "Reset Password", resetTokenMessage, contextPath, user.getUsername(), token);
        return new GenericResponse("Successfully sent Password Reset Email");
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


    // for 403 access denied page
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView pageNotFound() {
        ModelAndView model = new ModelAndView();
        model.setViewName("404");
        return model;
    }

}
