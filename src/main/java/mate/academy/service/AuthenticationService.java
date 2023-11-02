package mate.academy.service;

import javax.naming.AuthenticationException;
import mate.academy.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password) throws RegistrationException;
}
