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
    public User save(User user) {
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
            throw new DataProcessingException("Can't save new user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> getUserByLoginQuery =
                    session.createQuery("FROM User u "
                            + "WHERE u.login = :login ", User.class);
            getUserByLoginQuery.setParameter("login", login);
            return getUserByLoginQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a user by login: " + login, e);
        }
    }
}
