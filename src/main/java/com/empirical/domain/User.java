package com.empirical.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User extends BaseEntity {

    public enum Role {
        SUPER_ADMIN,
        COMPANY,
        EMPLOYEE
    }

    private String username;
    private String email;
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
