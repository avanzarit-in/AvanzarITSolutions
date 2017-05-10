package com.avanzarit.apps.vendormgmt.auth.validator;

import com.avanzarit.apps.vendormgmt.Tab;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Component
public class VendorValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Vendor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Vendor vendor = (Vendor) o;
        Map<String, String> tabInfo = getTabInfo(Vendor.class, Tab.class);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accno", "NotEmpty");
        if (vendor.getAccno().length() != 10) {
            errors.rejectValue("accno", "Size.vendor.accno");
        }
        if(errors.hasFieldErrors("accno")){
            vendor.setErrorOnTab(tabInfo.get("accno"));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty");
        if (vendor.getCountry().length() != 3) {
            errors.rejectValue("country", "Size.vendor.country");
            vendor.setErrorOnTab(tabInfo.get("country"));
        }
        if(errors.hasFieldErrors("country")){
            vendor.setErrorOnTab(tabInfo.get("country"));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (vendor.getName().length() > 35) {
            errors.rejectValue("name", "Size.vendor.name");
            vendor.setErrorOnTab(tabInfo.get("name"));
        }
        if(errors.hasFieldErrors("name")){
            vendor.setErrorOnTab(tabInfo.get("name"));
        }


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!isValidEmailAddress(vendor.getEmail())) {
            errors.rejectValue("email", "Invalid.vendor.email");
            vendor.setErrorOnTab(tabInfo.get("email"));
        }
        if (vendor.getEmail().length() > 250) {
            errors.rejectValue("email", "Size.vendor.email");
            vendor.setErrorOnTab(tabInfo.get("email"));
        }

        if(errors.hasFieldErrors("email")){
            if(vendor.getEmail().equalsIgnoreCase(",")){
                vendor.setEmail("");
            }
            vendor.setErrorOnTab(tabInfo.get("email"));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
        if (vendor.getAddress().length() > 250) {
            errors.rejectValue("address", "Size.vendor.address");
            vendor.setErrorOnTab(tabInfo.get("address"));
        }
        if(errors.hasFieldErrors("address")){
            vendor.setErrorOnTab(tabInfo.get("address"));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postcode", "NotEmpty");
        try {
            Integer.parseInt(vendor.getPostcode());
            if (vendor.getPostcode().length() != 6) {
                errors.rejectValue("postcode", "Size.vendor.postcode");
                vendor.setErrorOnTab(tabInfo.get("postcode"));
            }
        } catch (Exception e) {
            errors.rejectValue("postcode", "Invalid.vendor.postcode");
            vendor.setErrorOnTab(tabInfo.get("postcode"));
        }
        if(errors.hasFieldErrors("postcode")){
            vendor.setErrorOnTab(tabInfo.get("postcode"));
        }

    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private Map<String, String> getTabInfo(Class<?> cls, Class<?> annotation) {
        Map<String, String> data = new HashMap<>();
        for (Field field : cls.getDeclaredFields()) {
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].annotationType().equals(annotation)) {
                    data.put(name, ((Tab) annotations[i]).value());
                    break;
                }
            }

        }
        return data;
    }
}
