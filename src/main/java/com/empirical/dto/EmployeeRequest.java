package com.empirical.dto;

import java.math.BigDecimal;

public class EmployeeRequest extends UserRequest {

    private String position;
    private BigDecimal salary;

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
}
