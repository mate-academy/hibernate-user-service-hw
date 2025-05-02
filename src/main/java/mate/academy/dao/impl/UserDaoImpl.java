package mate.academy.dao.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Error adding user " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            try {
                return Optional.ofNullable((User) query.getSingleResult());
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DataProcessingException("Error finding user by email: " + email, e);
        }
    }
}
