package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

@Dao
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory factory) {
        super(factory);
    }

    public UserDaoImpl() {
        this(HibernateUtil.getSessionFactory());
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
        try (var session = factory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get a user by id:" + id, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (var session = factory.openSession()) {
            var query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Unable to find a user by email " + email, e);
        }
    }
}
