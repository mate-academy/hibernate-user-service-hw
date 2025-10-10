package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.AbstractDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@Dao
public class MovieSessionDaoImpl
        extends AbstractDao<MovieSession, Long>
        implements MovieSessionDao {

    public MovieSessionDaoImpl() {
        super(MovieSession.class);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT ms FROM MovieSession ms "
                                    + "JOIN FETCH ms.movie "
                                    + "JOIN FETCH ms.cinemaHall "
                                    + "WHERE function('DATE', ms.showTime) = :date "
                                    + "AND ms.movie.id = :movieId "
                                    + "ORDER BY ms.showTime ASC", MovieSession.class)
                    .setParameter("date", date)
                    .setParameter("movieId", movieId)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Cant find available sessions", e);
        }
    }
}
