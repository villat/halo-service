package com.empirical.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    private String name;
    private String type;

    @OneToOne(mappedBy = "company")
    private Owner owner;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public static final class Builder {
        protected Date createdAt = new Date();
        protected Date updatedAt = new Date();
        private String name;
        private String type;
        private Owner owner;
        private List<Employee> employees;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Builder withEmployees(List<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Company build() {
            Company company = new Company();
            company.setName(name);
            company.setType(type);
            company.setEmployees(employees);
            company.setCreatedAt(createdAt);
            company.setUpdatedAt(updatedAt);
            company.owner = this.owner;
            return company;
        }
    }
}
