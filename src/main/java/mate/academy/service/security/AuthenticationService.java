package mate.academy.service.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    void register(String email, String password) throws RegistrationException;

    User authenticate(String email, String password) throws AuthenticationException;
}
