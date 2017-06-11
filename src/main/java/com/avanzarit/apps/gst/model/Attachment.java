package com.avanzarit.apps.gst.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by AVANZAR on 6/11/2017.
 */
@Entity
@Table(name = "attachment")
public class Attachment {
    public static final String PAN = "PAN";
    private Long id;
    private String docType;
    private String docName;
    private Vendor vendor;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "doctype")
    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Column(name = "docname")
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
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
        Attachment that = (Attachment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(docType, that.docType) &&
                Objects.equals(docName, that.docName) &&
                Objects.equals(vendor, that.vendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, docType, docName, vendor);
    }
}
