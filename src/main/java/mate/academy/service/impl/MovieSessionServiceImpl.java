package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao sessionDao;

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date)
            throws EntityNotFoundException {
        List<MovieSession> movieSessions = sessionDao.findAvailableSessions(movieId, date);
        if (movieSessions.isEmpty()) {
            throw new EntityNotFoundException("Can't find any movie session. Movie id: " + movieId
            + ". Date: " + date);
        }
        return sessionDao.findAvailableSessions(movieId, date);
    }

    @Override
    public MovieSession get(Long id) throws EntityNotFoundException {
        return sessionDao.get(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find movie session by id: " + id));
    }

    @Override
    public MovieSession add(MovieSession session) {
        return sessionDao.add(session);
    }
}
