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
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        System.out.println("dao login : " + login);
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM User WHERE login = :login");
            query.setParameter("login", login);
            User user = (User) query.uniqueResult();
            transaction.commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            transaction.rollback();
            throw new DataProcessingException("Error finding user by email: " + login, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static Session getSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }
}
