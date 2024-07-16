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
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil
                    .getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add a user: \""
                    + user.getEmail() + "\" to the DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            String hql = "from User as u where u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find a user with email:\""
                    + email + "\"", e);
        }
    }
}
