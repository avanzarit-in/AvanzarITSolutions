package com.avanzarit.apps.gst.auth.db.model;

import com.avanzarit.apps.gst.Model;
import com.avanzarit.apps.gst.auth.AUTH_SYSTEM;
import com.avanzarit.apps.gst.auth.model.AppUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "appuser")
public class DbUser implements Model, AppUser {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private String mobile;
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

    @Override
    @Transient
    public String getUserId() {
        return getUsername();
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    @Override
    @Transient
    public List<String> getRolesAsString() {
        return getRoles().stream().map(item->item.getName()).collect(Collectors.toList());
    }

    @Override
    @Transient
    public AUTH_SYSTEM getAuthSystem() {
        return AUTH_SYSTEM.DB;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
