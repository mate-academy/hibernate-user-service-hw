package mate.academy.dao.impl;

import mate.academy.dao.UserDao;
import java.util.Optional;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot save user", exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception exception) {
            throw new RuntimeException("Cannot get user", exception);
        }
    }

    @Override
    public Optional<User>  findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where email"
                    + "=:email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception exception) {
            throw new RuntimeException("Cannot get user", exception);
        }
    }
}
