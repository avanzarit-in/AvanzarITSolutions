package com.avanzarit.apps.vendormgmt.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthHandler implements ExceptionMappingAuthenticationFailureHandler {
    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();

public AuthHandler(){

}
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication auth) {
        String userName=auth.getPrincipal().toString();
        request.getSession(false).setAttribute("USERID",userName);

    }



    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletRequest.getSession(false).setAttribute("USERID","dfdF");
    }
}
