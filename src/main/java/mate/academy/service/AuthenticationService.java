package mate.academy.service;

import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User register(String email, String password) throws RegistrationException;

    User login(String login, String password) throws AuthenticationException;
}
