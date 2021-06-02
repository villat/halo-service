package com.empirical.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee extends User {

    private String position;
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public static final class Builder {
        protected Date createdAt = new Date();
        protected Date updatedAt = new Date();
        private String position;
        private BigDecimal salary;
        private Company company;
        private String username;
        private String email;
        private Role role;

        private Builder() {
        }

        public static Builder anEmployee() {
            return new Builder();
        }

        public Builder withPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder withSalary(BigDecimal salary) {
            this.salary = salary;
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

        public Employee build() {
            Employee employee = new Employee();
            employee.setPosition(position);
            employee.setSalary(salary);
            employee.setCompany(company);
            employee.setUsername(username);
            employee.setEmail(email);
            employee.setRole(role);
            employee.setCreatedAt(createdAt);
            employee.setUpdatedAt(updatedAt);
            return employee;
        }
    }
}
