package mate.academy.dao.impl;

import java.util.List;
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
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add user to database"
                    + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user "
                    + "from db with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("Select u FROM User u", User.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all users "
                    + "from db.", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("Select u FROM User u "
                            + "WHERE u.email = :email", User.class)
                    .setParameter("email", email).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user "
                    + "from db with email: " + email, e);
        }
    }
}
