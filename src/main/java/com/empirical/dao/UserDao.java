package com.empirical.dao;

import com.empirical.domain.Owner;
import com.empirical.domain.User;
import com.empirical.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDao extends DaoUtils<User> implements GenericDao<User> {

    @Override
    public Optional<User> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(User user) {

    }

    @Override
    public void delete(User user) {

    }

    public User getByUsername(String username){
        User response;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where username=:username")
                    .setParameter("username", username);
            response = (User) query.uniqueResult();
            session.close();
        }
        return response;
    }
}
