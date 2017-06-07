package com.avanzarit.apps.gst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by AVANZAR on 6/7/2017.
 */
@Entity
@Table(name = "hsnmaster")
public class HsnMaster {
    private Long id;
    private String code;

    @JsonIgnore
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
