package com.empirical.dao;

import com.empirical.domain.SuperAdmin;
import com.empirical.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SuperAdminDao extends DaoUtils<SuperAdmin> implements GenericDao<SuperAdmin> {

    @Override
    public Optional<SuperAdmin> get(Long id) {
        return Optional.ofNullable(execute(session -> session.get(SuperAdmin.class, id)));
    }

    @Override
    public List<SuperAdmin> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(SuperAdmin superAdmin) {
        executeInsideTransaction(session -> session.saveOrUpdate(superAdmin));
    }

    @Override
    public void delete(SuperAdmin owner) {

    }

    public SuperAdmin getByUsername(String username){
        SuperAdmin response;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query query = session.createQuery("from SuperAdmin where username=:username")
                    .setParameter("username", username);
            response = (SuperAdmin) query.uniqueResult();
            session.close();
        }
        return response;
    }
}
