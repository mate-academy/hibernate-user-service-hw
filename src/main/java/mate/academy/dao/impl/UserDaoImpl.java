package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.persist(user);
            transaction.commit();

            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query =
                    session.createQuery("FROM User AS u "
                            + "WHERE u.login = :login", User.class);
            query.setParameter("login", login);

            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Cant find a login: " + login, e);
        }
    }
}
