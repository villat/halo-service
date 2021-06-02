package com.empirical.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "super_admins")
public class SuperAdmin extends User {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
