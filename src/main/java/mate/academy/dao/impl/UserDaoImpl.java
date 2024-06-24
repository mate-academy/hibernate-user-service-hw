package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Dao
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public User save(User user) {
        return executeTransaction(session -> {
            session.persist(user);
            return user;
        });
    }

    @Override
    public Optional<User> get(Long id) {
        try {
            var session = factory.openSession();
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get a user by id:" + id, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Session session = factory.openSession()) {
            var query = session.createQuery("FROM User u WHERE u.login = :login", User.class);
            query.setParameter("login", login);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Unable to find a user by login " + login, e);
        }
    }
}
