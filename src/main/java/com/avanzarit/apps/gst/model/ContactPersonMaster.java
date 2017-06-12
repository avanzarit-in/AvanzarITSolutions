package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.batch.job.annotations.Export;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by SPADHI on 5/31/2017.
 */
@Entity
@Table(name = "contactpersonmaster")
public class ContactPersonMaster implements Model {

    private Long id;
    @Export(order = 2, title = "LASTNAME")
    private String lastName;
    @Export(order = 3, title = "FIRSTNAME")
    private String firstName;
    @Export(order = 4, title = "DEPT")
    private String department;
    @Export(order = 5, title = "MOBILE")
    private String mobile;
    @Export(order = 6, title = "TELEPHONE")
    private String telephone;
    @Export(order = 7, title = "EMAIL")
    private String email;
    @Export(order = 1, title = "VENDORID")
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
        return Objects.equals(id, that.id) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(department, that.department) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, department, mobile, telephone, email);
    }

    @Override
    public String toString() {
        return "ContactPersonMaster{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
