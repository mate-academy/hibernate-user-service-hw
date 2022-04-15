package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    public void register(String email, String password) throws RegistrationException;

    public User login(String email, String password) throws AuthenticationException;
}
