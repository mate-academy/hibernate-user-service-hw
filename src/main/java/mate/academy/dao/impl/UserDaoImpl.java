package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot save user entity " + user + " in DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Cannot find user by ID=" + id + " from DB", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> userByEmail = session
                    .createQuery("FROM User WHERE User.email LIKE :e_mail", User.class);
            userByEmail.setParameter("e_mail", email);
            return userByEmail.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user by email=" + email + " from DB", e);
        }
    }
}
