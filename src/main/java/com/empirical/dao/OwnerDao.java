package com.empirical.dao;

import com.empirical.domain.Owner;
import com.empirical.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OwnerDao extends DaoUtils<Owner> implements GenericDao<Owner> {

    @Override
    public Optional<Owner> get(Long id) {
        return Optional.ofNullable(execute(session -> session.get(Owner.class, id)));
    }

    @Override
    public List<Owner> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(Owner owner) {
        executeInsideTransaction(session -> session.saveOrUpdate(owner));
    }

    @Override
    public void delete(Owner owner) {

    }

    public Owner getByUsername(String username){
        Owner response;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Owner where username=:username")
                    .setParameter("username", username);
            response = (Owner) query.uniqueResult();
            session.close();
        }
        return response;
    }
}
