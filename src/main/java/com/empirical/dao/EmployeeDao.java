package com.empirical.dao;

import com.empirical.domain.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeDao extends DaoUtils<Employee> implements GenericDao<Employee> {


    @Override
    public Optional<Employee> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(Employee employee) {

    }

    @Override
    public void delete(Employee employee) {

    }
}
