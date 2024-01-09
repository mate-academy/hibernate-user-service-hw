package mate.academy.service;

import java.util.List;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.Movie;

public interface MovieService {
    Movie add(Movie movie);

    Movie get(Long id) throws EntityNotFoundException;

    List<Movie> getAll() throws EntityNotFoundException;
}
