package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public User login(String email, String password) throws AuthenticationException {
        return null;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return null;
    }
}
