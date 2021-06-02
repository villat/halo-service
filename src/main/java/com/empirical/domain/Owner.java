package com.empirical.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "owners")
public class Owner extends User {

    @Column(name = "create_by")
    private String createdBy;

    @OneToOne
    @JoinColumn(name="company_id", referencedColumnName = "id")
    private Company company;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public static final class Builder {
        private String createdBy;
        private Company company;
        private String username;
        private String email;
        private Role role;
        private Date createdAt = new Date();
        private Date updatedAt = new Date();

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder withCompany(Company company) {
            this.company = company;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
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

        public Owner build() {
            Owner owner = new Owner();
            owner.setCreatedBy(createdBy);
            owner.setCompany(company);
            owner.setUsername(username);
            owner.setEmail(email);
            owner.setRole(role);
            owner.setCreatedAt(createdAt);
            owner.setUpdatedAt(updatedAt);
            return owner;
        }
    }
}
