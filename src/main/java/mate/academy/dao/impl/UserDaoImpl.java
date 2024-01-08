package mate.academy.dao.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.MovieSessionException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add user " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user by id: " + id, e);
        }
    }

    @Override
    public Optional<User> get(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> userQuery = session.createQuery("FROM User u "
                    + "WHERE u.email = :e", User.class);
            userQuery.setParameter("e", email);
            return Optional.ofNullable(userQuery.getSingleResultOrNull());
        }
    }

    @Override
    public User updateMovieSession(String email, MovieSession movieSession)
            throws MovieSessionException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = get(email).orElseThrow();
            if (movieSession.getShowTime().isBefore(LocalDateTime.now())) {
                throw new MovieSessionException("This movie session is already passed. Session: "
                        + movieSession);
            }
            user.setMovieSession(movieSession);
            session.merge(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new MovieSessionException("Cannot update movie session. mail: " + email
                    + ". Movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
