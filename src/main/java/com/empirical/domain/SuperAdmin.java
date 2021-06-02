package com.empirical.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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


    public static final class Builder {
        protected Date createdAt = new Date();
        protected Date updatedAt = new Date();
        private String description;
        private String username;
        private String email;
        private Role role;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withDescription(String description) {
            this.description = description;
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

        public SuperAdmin build() {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setDescription(description);
            superAdmin.setUsername(username);
            superAdmin.setEmail(email);
            superAdmin.setRole(role);
            superAdmin.setCreatedAt(createdAt);
            superAdmin.setUpdatedAt(updatedAt);
            return superAdmin;
        }
    }
}
