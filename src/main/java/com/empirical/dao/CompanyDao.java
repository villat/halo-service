package com.empirical.dao;

import com.empirical.domain.Company;

import java.util.List;
import java.util.Optional;

public class CompanyDao extends DaoUtils<Company> implements GenericDao<Company> {

    @Override
    public Optional<Company> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Company> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(Company company) {
        executeInsideTransaction(session -> session.saveOrUpdate(company));
    }

    @Override
    public void delete(Company company) {

    }
}
