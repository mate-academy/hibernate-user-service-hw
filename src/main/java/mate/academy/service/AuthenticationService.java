package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String mail, String password) throws AuthenticationException;

    User register(String email, String password) throws AuthenticationException;
}
