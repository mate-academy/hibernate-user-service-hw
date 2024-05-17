package mate.academy.dao.impl;

import mate.academy.dao.UserDAO;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Optional;

@Dao
public class UserDAOImpl implements UserDAO {
    @Override
    public User save(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot add user" + user.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> findByMail(String mail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "from User u WHERE u.mail = :mail";
            Query<User> query = session.createQuery(sql).setParameter("mail", mail);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user by email: " + mail, e);
        }
    }
}
