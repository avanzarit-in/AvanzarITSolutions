package com.avanzarit.apps.gst.auth.handler;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by SPADHI on 5/31/2017.
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    public CustomAuthenticationSuccessHandler() {
        super();

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //do some logic here if you want something to be done whenever
        //the user successfully logs in.

        HttpSession session = httpServletRequest.getSession();
        String userName=authentication.getName();
        User user=userService.findByUsername(userName);
        user.setLastLoginDate(new Date());
        userService.saveOnly(user);

        //set our response to OK status
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        //  httpServletResponse.sendRedirect("/");

    }
}
