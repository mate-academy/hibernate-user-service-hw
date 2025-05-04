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
    private static final String CAN_T_ADD_NEW_USER_MSG = "Can't add new user";
    private static final String SPACE = " ";
    private static final String EMAIL = "email";
    private static final String CAN_T_RECEIVE_USER_BY_EMAIL_MSG = "Can't receive user by email ";

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CAN_T_ADD_NEW_USER_MSG + SPACE + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> userQuery =
                    session.createQuery("SELECT u FROM User u where u.email=:email", User.class);
            userQuery.setParameter(EMAIL, email);
            User user = userQuery.getSingleResultOrNull();
            if (user == null) {
                return Optional.empty();
            }
            return Optional.of(user);
        } catch (RuntimeException e) {
            throw new DataProcessingException(CAN_T_RECEIVE_USER_BY_EMAIL_MSG + email, e);
        }
    }
}
