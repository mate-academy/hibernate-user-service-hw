package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    @Inject
    private SessionFactory sessionFactory;

    public UserDaoImpl() {
    }

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not save user" + user, e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public User get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        }
    }
}
