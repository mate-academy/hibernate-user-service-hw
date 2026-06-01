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
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transactionNullCheck(transaction);
            throw new DataProcessingException("Could not persist user: " + user, e);
        } finally {
            closeSession(session);
        }
    }

    @Override
    public User get(int id) {
        try (Session session = openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Could not find user with id: " + id, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = openSession()) {
            return session.createQuery("from User u where u.email = :email", User.class)
                    .setParameter("email", email).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Could not find user with email: " + email, e);
        }
    }

    private Session openSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }

    private void transactionNullCheck(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
