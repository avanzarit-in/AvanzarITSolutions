package com.avanzarit.apps.gst.auth.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by SPADHI on 5/4/2017.
 */
@Entity
@Table(name = "appuser")
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
    private Date lastLoginDate;
    private UserStatusEnum userStatus;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
      return roles;
    }

    public void setRoles(Set<Role> roles) {
       this.roles = roles;
    }

    @Column(name="lastlogin")
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Column(name="userstatus")
    public UserStatusEnum getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
