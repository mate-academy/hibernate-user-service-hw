package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save a user "
                    + user + "in a DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> userQuery = session.createQuery("from User u "
                    + "where u.email = : value", User.class);
            userQuery.setParameter("value", email);
            return Optional.ofNullable(userQuery.uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't find a user by email "
                    + email + " in a DB!", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> sessionQueryLogin = session.createQuery("from User u "
                    + "where u.login = :value", User.class);
            sessionQueryLogin.setParameter("value", login);
            return Optional.ofNullable(sessionQueryLogin.uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't find a user by login "
                    + login + " in a DB!", e);
        }
    }
}
