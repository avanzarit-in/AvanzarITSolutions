package com.avanzarit.apps.vendormgmt.model;

import com.avanzarit.apps.vendormgmt.Tab;
import com.avanzarit.apps.vendormgmt.utils.Utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Entity
@Table(name = "vendor")
public class Vendor {


    private Long id;
    @Tab("1")
    private String accno;
    @Tab("2")
    private String country;
    @Tab("1")
    private String name;
    @Tab("3")
    private String email;
    @Tab("2")
    private String address;
    @Tab("2")
    private String postcode;
    @Tab("2")
    private String city;
    @Tab("2")
    private String region;
    @Tab("2")
    private String pobox;
    @Tab("2")
    private String popostalcode;
    @Tab("2")
    private String copostalcode;
    @Tab("3")
    private String language;
    @Tab("3")
    private String telephone;
    @Tab("3")
    private String phext;
    @Tab("3")
    private String fxext;
    @Tab("3")
    private String mobile;
    @Tab("3")
    private String faxno;
    @Tab("3")
    private String dataline;
    @Tab("3")
    private String telebox;
    private List<String> tabErrorList = new ArrayList<>();


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getPopostalcode() {
        return popostalcode;
    }

    public void setPopostalcode(String popostalcode) {
        this.popostalcode = popostalcode;
    }

    public String getCopostalcode() {
        return copostalcode;
    }

    public void setCopostalcode(String copostalcode) {
        this.copostalcode = copostalcode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhext() {
        return phext;
    }

    public void setPhext(String phext) {
        this.phext = phext;
    }

    public String getFxext() {
        return fxext;
    }

    public void setFxext(String fxext) {
        this.fxext = fxext;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getDataline() {
        return dataline;
    }

    public void setDataline(String dataline) {
        this.dataline = dataline;
    }

    public String getTelebox() {
        return telebox;
    }

    public void setTelebox(String telebox) {
        this.telebox = telebox;
    }

    @Transient
    public Map<String, String> getCountryMap() {
        return Utils.country;
    }

    @Transient
    public Map<String, String> getCityMap() {
        return Utils.city;
    }

    @Transient
    public Map<String, String> getRegionMap() {
        return Utils.region;
    }

    @Transient
    public Map<String, String> getLanguageMap() {
        return Utils.language;
    }

    @Transient
    public List<String> getErrorOnTab() {
        return tabErrorList;
    }

    public void setErrorOnTab(String tabNo) {
        tabErrorList.add(tabNo);
    }
}
