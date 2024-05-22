package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add user to DB " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public boolean checkLoginExists(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT COUNT(u) "
                            + "FROM User u WHERE u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
            System.out.println(count);
            return count != 0;
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user with login: " + login, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        }
    }
}
