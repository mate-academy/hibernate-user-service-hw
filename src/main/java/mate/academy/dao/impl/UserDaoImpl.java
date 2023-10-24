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
    public User add(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Error e) {
            if (transaction != null) {
                transaction.rollback();
                throw new DataProcessingException("Can't insert user: " + user, e);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<User> findByEmailQuery = session.createQuery(
                    "from User u where u.email = :email", User.class);
            Query<User> userFromDb = findByEmailQuery.setParameter("email", email);
            return userFromDb.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user by this email: " + email, e);
        }
    }
}
