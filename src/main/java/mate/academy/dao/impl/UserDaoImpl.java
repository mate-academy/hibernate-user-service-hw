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

    public static final String CANT_ADD_USER_EXCEPTION_MESSAGE =
            "Can't add user to DB: ";
    public static final String CANT_FIND_USER_BY_EMAIL_EXCEPTION_MESSAGE =
            "Can't get user from DB by email: ";
    private static final String GET_USER_BY_EMAIL_QUERY =
            "FROM User us WHERE us.email = :email";
    private static final String USER_EMAIL_PARAMETER = "email";
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CANT_ADD_USER_EXCEPTION_MESSAGE + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = factory.openSession()) {
            Query<User> getUserByEmailQuery = session.createQuery(
                    GET_USER_BY_EMAIL_QUERY, User.class);
            getUserByEmailQuery.setParameter(USER_EMAIL_PARAMETER, email);
            return getUserByEmailQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException(
                    CANT_FIND_USER_BY_EMAIL_EXCEPTION_MESSAGE + email, e);
        }
    }
}
