package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.MovieDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie get(Long id) throws EntityNotFoundException {
        return movieDao.get(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find movie by id: " + id));
    }

    @Override
    public List<Movie> getAll() throws EntityNotFoundException {
        List<Movie> movies = movieDao.getAll();
        if (movies.isEmpty()) {
            throw new EntityNotFoundException("Can't find any movie by id");
        }
        return movieDao.getAll();
    }
}
