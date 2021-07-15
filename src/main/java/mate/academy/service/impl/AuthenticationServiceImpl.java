package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public User login(String email, String password) throws AuthenticationException {
        return null;
    }

    @Override
    public User register(String email, String password) {
        return null;
    }
}
