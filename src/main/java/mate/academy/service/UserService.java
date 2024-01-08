package mate.academy.service;

import mate.academy.exception.MovieSessionException;
import mate.academy.model.MovieSession;
import mate.academy.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    User get(String email);

    public User update(String email, MovieSession movieSession) throws MovieSessionException;
}
