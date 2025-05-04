package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public User add(User user) throws RegistrationException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RegistrationException("Can't save new user with email "
                    + user.getEmail() + ", this email is not available. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        String query = "FROM User WHERE email = :email";
        try (Session session = factory.openSession()) {
            Query<User> getUserQuery = session.createQuery(query);
            getUserQuery.setParameter("email", email);
            return Optional.ofNullable(getUserQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user with email "
                    + email + ". ", e);
        }
    }
}
