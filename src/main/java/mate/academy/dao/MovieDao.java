package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;

@Dao
public interface MovieDao {
    Movie add(Movie movie);

    Optional<Movie> get(Long id);

    List<Movie> getAll();
}
