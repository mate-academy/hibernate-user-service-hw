package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Movie;

public interface MovieDao {
    Movie add(Movie movie);

    Optional<Movie> findById(Long id);

    List<Movie> findAll();
}
