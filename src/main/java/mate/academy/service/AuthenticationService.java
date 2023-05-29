package mate.academy.service;

import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import javax.naming.AuthenticationException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
