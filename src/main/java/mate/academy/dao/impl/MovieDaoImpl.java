package mate.academy.dao.impl;

import mate.academy.dao.AbstractDao;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;

@Dao
public class MovieDaoImpl extends AbstractDao<Movie, Long> implements MovieDao {

    public MovieDaoImpl() {
        super(Movie.class);
    }
}
