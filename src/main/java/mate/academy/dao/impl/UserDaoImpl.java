package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add new User " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByMail(String mail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User u where u.mail = :mail", User.class)
                    .setParameter("mail", mail)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can't find user by email " + mail, e);
        }
    }
}
