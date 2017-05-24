package com.avanzarit.apps.vendormgmt;

import com.avanzarit.apps.vendormgmt.auth.handler.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthHandler handler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ExceptionMappingAuthenticationFailureHandler getFailureHandler() {
        Map<String, String> exceptionMappings = new HashMap<>();
        exceptionMappings.put("org.springframework.security.authentication.CredentialsExpiredException", "/passwordExpired");
        ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();
        failureHandler.setExceptionMappings(exceptionMappings);

        return failureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .authorizeRequests()
                .antMatchers("/updatePassword", "/passwordExpired")
                .hasAuthority("ADMIN")
                .antMatchers("/updatePassword", "/passwordExpired","/images/**", "/css/**", "/js/**", "/upload", "/userupload", "/vendorDataUploadForm", "/add", "/registration", "/uploadVendor", "/vendorListView", "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureHandler(getFailureHandler())

                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
