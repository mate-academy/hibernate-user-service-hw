package mate.academy.service.impl;

import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.MovieSessionException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(() ->
                new DataProcessingException("Cannot find user by id: " + id));
    }

    @Override
    public User get(String email) {
        return userDao.get(email).orElseThrow(() ->
                new DataProcessingException("Cannot find user by email: " + email));
    }

    @Override
    public User update(String email, MovieSession movieSession) throws MovieSessionException {
        return userDao.updateMovieSession(email, movieSession);
    }
}
