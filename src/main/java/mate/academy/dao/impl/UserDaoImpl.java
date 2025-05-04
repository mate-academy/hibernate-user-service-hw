package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = SESSION_FACTORY.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't inject user " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createQuery("FROM User user WHERE user.login = :value",
                            User.class)
                    .setParameter("value", email)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can't find an user by id", e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = SESSION_FACTORY.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createQuery("FROM User ", User.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all users from db", e);
        }
    }
}
