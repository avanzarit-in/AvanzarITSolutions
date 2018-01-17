package com.avanzarit.apps.gst.auth.handler;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            System.out.println(authentication.getName());
        }
        //perform other required operation
        String URL = request.getContextPath() + "/app";
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(URL);
    }
}
