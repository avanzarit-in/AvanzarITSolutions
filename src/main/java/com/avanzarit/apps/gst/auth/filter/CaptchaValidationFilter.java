package com.avanzarit.apps.gst.auth.filter;

import com.avanzarit.apps.gst.auth.handler.CustomAuthenticationSuccessHandler;
import com.avanzarit.apps.gst.auth.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by SPADHI on 5/25/2017.
 */
@Component
public class CaptchaValidationFilter extends UsernamePasswordAuthenticationFilter implements InitializingBean {


    private final String CAPTCHA_RESPONSE_FIELD = "g-recaptcha-response";
    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler=new SimpleUrlAuthenticationFailureHandler("/login?error=true");

    public CaptchaValidationFilter(){
        super();
        super.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler);
     }
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String reCaptchaResponse = request.getParameter(CAPTCHA_RESPONSE_FIELD);
        String remoteAddress = request.getRemoteAddr();
        return super.attemptAuthentication(request, response);
    }

    @Autowired
    public void setCustomAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandle) {
        super.setAuthenticationSuccessHandler(customAuthenticationSuccessHandle);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}