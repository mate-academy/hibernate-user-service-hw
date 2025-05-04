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
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public User add(User user) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }

                throw new DataProcessingException(String.format("Can't add "
                        + "user: %s to DB", user), e);
            }
        }

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = factory.openSession()) {
            Query<User> findUserByEmailQuery = session.createQuery(
                    "FROM User user WHERE user.email = :email", User.class);
            findUserByEmailQuery.setParameter("email", email);

            return findUserByEmailQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException(String.format("Can't get user "
                    + "by email: %s from DB", email), e);
        }
    }
}
