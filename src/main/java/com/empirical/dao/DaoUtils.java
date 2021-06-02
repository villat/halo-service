package com.empirical.dao;

import com.empirical.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DaoUtils<T> {

    protected T execute(Function<Session, T> action) {
        T response;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            response = action.apply(session);
            session.close();
        }
        return response;
    }

    protected void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
