package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.EmailAlreadyExistsException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws EmailAlreadyExistsException;
}
