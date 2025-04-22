package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie " + movie, e);
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Movie.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie by id: " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Movie").list();
        }
    }

    @Override
    public boolean update(Movie movie) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(movie);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Updating " + movie + " failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(get(id)
                    .orElseThrow(() -> new RuntimeException("no object with id " + id)));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("removing movie with id: "
                    + id + " from database failed", e);
        }
        return get(id).isEmpty();
    }
}
