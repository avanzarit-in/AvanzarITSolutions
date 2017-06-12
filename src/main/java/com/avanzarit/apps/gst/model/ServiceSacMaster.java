package com.avanzarit.apps.gst.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.batch.job.annotations.Export;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by SPADHI on 5/18/2017.
 */
@Entity
@Table(name = "servicesacmaster")
public class ServiceSacMaster implements Model {

    private Long id;
    @Export(order = 2, title = "CODE")
    private String code;
    @Export(order = 3, title = "DESCRIPTION")
    private String desc;
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

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @ManyToOne
    @JoinColumn(name = "vendorid")
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceSacMaster that = (ServiceSacMaster) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, desc);
    }

    @Transient
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "ServiceSacMaster{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
