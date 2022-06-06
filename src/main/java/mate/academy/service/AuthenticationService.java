package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    public User login(String email, String password) throws AuthenticationException;

    public User register(String email, String password) throws RegistrationException;
}
