package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User register(String email, String password) throws AuthenticationException;

    User login(String login, String password) throws AuthenticationException;
}
