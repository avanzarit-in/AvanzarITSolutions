package com.avanzarit.apps.gst;

import com.avanzarit.apps.gst.auth.filter.CaptchaValidationFilter;
import com.avanzarit.apps.gst.auth.filter.CustomFilter;
import com.avanzarit.apps.gst.auth.handler.LogoutSuccessHandlerImpl;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private CaptchaValidationFilter captchaValidationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(captchaValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**/updatePassword").hasAuthority("ROLE_PASSWORD_CHANGE")
                .antMatchers("/upload/user", "/adminLanding", "/logs/**").hasAnyAuthority("ADMIN", "BUSINESS_OWNER_CUSTOMER", "BUSINESS_OWNER_VENDOR")
                .antMatchers("/customerListView", "/upload/customer").hasAnyAuthority("ADMIN", "BUSINESS_OWNER_CUSTOMER")
                .antMatchers("/vendorListView", "/upload").hasAnyAuthority("ADMIN", "BUSINESS_OWNER_VENDOR")
                .antMatchers("/userListView").hasAnyAuthority("BUSINESS_OWNER_CUSTOMER", "ADMIN", "BUSINESS_OWNER_VENDOR")
                .antMatchers("/businessOwnerLanding").hasAnyAuthority("BUSINESS_OWNER_VENDOR", "BUSINESS_OWNER_CUSTOMER")
                .antMatchers("/get/customer/**").hasAnyAuthority("CUSTOMER", "ADMIN", "BUSINESS_OWNER_CUSTOMER")
                .antMatchers("/get", "/get/**").hasAnyAuthority("VENDOR", "ADMIN", "BUSINESS_OWNER_VENDOR")
                .antMatchers("/mdm/*").hasAnyAuthority( "ADMIN","PO_ADMIN","PO_FINANCE","PO_TAX","PO_ORG","PO_ADMIN_MANAGER","PO_FINANCE_MANAGER","PO_TAX_MANAGER","PO_ORG_MANAGER")
                .antMatchers("/download", "/changePassword", "/resetPassword", "/master/**", "/images/**", "/css/**", "/js/**", "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/**/logout")).logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }
}
