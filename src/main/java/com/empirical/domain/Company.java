package com.empirical.domain;

import javax.persistence.Entity;

@Entity(name = "companies")
public class Company extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
