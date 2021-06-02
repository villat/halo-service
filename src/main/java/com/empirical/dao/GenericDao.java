package com.empirical.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    Optional<T> get(Long id);

    List<T> getAll();

    void saveOrUpdate(T t);

    void delete(T t);

}
