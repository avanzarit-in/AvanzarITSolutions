package com.avanzarit.apps.gst.auth.db.model;

import com.avanzarit.apps.gst.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements Model {
    private Long id;
    private String name;
    private Set<DbUser> dbUsers;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<DbUser> getDbUsers() {
        return dbUsers;
    }

    public void setDbUsers(Set<DbUser> dbUsers) {
        this.dbUsers = dbUsers;
    }
}
