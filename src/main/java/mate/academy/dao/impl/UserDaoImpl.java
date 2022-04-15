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
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert user" + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = sessionFactory.openSession();) {
            Query<User> getUserByIdQuery =
                    session.createQuery("FROM User u "
                            + "WHERE u.id = :id ", User.class);
            getUserByIdQuery.setParameter("id", id);
            return getUserByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user by id: " + id, e);
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        try (Session session = sessionFactory.openSession();) {
            Query<User> getUserByIdQuery =
                    session.createQuery("FROM User u "
                            + "WHERE u.email = :email ", User.class);
            getUserByIdQuery.setParameter("email", email);
            return getUserByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user by email: " + email, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession();) {
            Query<User> getUsersQuery =
                    session.createQuery("FROM User", User.class);
            return getUsersQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }
}
