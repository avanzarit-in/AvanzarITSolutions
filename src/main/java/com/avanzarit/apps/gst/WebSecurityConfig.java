package com.avanzarit.apps.gst;

import com.avanzarit.apps.gst.auth.filter.CaptchaValidationFilter;
import com.avanzarit.apps.gst.auth.filter.CustomFilter;
import com.avanzarit.apps.gst.auth.handler.LogoutSuccessHandlerImpl;
import com.avanzarit.apps.gst.auth.ldap.model.LdapUser;
import com.avanzarit.apps.gst.auth.ldap.properties.LdapProperties;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Order(value = 1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private CaptchaValidationFilter captchaValidationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserDetailsService userDetailsService;

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
                .antMatchers("/mdm","/mdm/**","/mdm/get/**").hasAnyAuthority("ADMIN", "PO_ADMIN", "PO_FINANCE", "PO_TAX", "PO_ORG", "PO_ADMIN_MANAGER", "PO_FINANCE_MANAGER", "PO_TAX_MANAGER", "PO_ORG", "PO_ORG_MANAGER")
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
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .addHeaderWriter(new XXssProtectionHeaderWriter())
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

  /*  @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    } */


    @Autowired
    LdapProperties ldapProperties;

    @Bean
    public LdapShaPasswordEncoder ldapShaPasswordEncoder() {
        return new LdapShaPasswordEncoder();
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:10389");
        contextSource.setBase("o=avanzarit");
        contextSource.setUserDn(ldapProperties.getManagerDn());
        contextSource.setPassword(ldapProperties.getManagerPassword());
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Autowired

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth
                .ldapAuthentication()
                .userDnPatterns(ldapProperties.getUserDnPatterns())
                .userSearchFilter(ldapProperties.getUserSearchFilter())
                .userSearchBase(ldapProperties.getUserSearchBase())
                .groupSearchBase(ldapProperties.getGroupSearchBase())
                .groupSearchFilter(ldapProperties.getGroupSearchFilter())
                .userDetailsContextMapper(userDetailsContextMapper())
                .rolePrefix(ldapProperties.getRolePrefix())
                .contextSource()
                .url(ldapProperties.getUrl())
                .managerDn(ldapProperties.getManagerDn())
                .managerPassword(ldapProperties.getManagerPassword())
                .and()
                .passwordCompare()
                .passwordEncoder(ldapShaPasswordEncoder())
                .passwordAttribute(ldapProperties.getPasswordAttribute());

    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new LdapUserDetailsMapper() {
            @Override
            public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
                UserDetails details = super.mapUserFromContext(ctx, username, authorities);
                LdapUser ldapUser=new LdapUser();
                ldapUser.setAttributes(ctx.getAttributes());
                ldapUser.setDetails((LdapUserDetails) details);
                return ldapUser;
            }
        };
    }
}
