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
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot add user " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query<User> getByEmailQuery = session.createQuery("from User u where u.email = :email",
                    User.class);
            getByEmailQuery.setParameter("email", email);

            Optional<User> result = Optional.ofNullable(getByEmailQuery.getSingleResult());
            transaction.commit();

            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
