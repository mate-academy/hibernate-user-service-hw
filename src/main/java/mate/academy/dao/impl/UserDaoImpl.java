package mate.academy.dao.impl;

import static mate.academy.util.HibernateUtil.getSessionFactory;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert user " + user, exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email).uniqueResultOptional();
        } catch (Exception exception) {
            throw new DataProcessingException("Can't find user by email: " + email, exception);
        }
    }
}
