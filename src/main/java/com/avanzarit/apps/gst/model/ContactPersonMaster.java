package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

/**
 * Created by SPADHI on 5/31/2017.
 */
@Entity
@Table(name = "contactpersonmaster")
public class ContactPersonMaster implements Model {

    private Long id;
    private String lastName;
    private String firstName;
    private String department;
    private String mobile;
    private String telephone;
    private String email;
    private String vendorId;
    private Vendor vendor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "vendorid")
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Transient
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPersonMaster that = (ContactPersonMaster) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getDepartment(), that.getDepartment()) &&
                Objects.equals(getMobile(), that.getMobile()) &&
                Objects.equals(getTelephone(), that.getTelephone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getVendor(), that.getVendor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLastName(), getFirstName(), getDepartment(), getMobile(), getTelephone(), getEmail(), getVendor());
    }
}
