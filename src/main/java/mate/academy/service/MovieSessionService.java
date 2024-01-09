package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.MovieSession;

public interface MovieSessionService {
    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date)
            throws EntityNotFoundException;

    MovieSession get(Long id) throws EntityNotFoundException;

    MovieSession add(MovieSession session);
}
