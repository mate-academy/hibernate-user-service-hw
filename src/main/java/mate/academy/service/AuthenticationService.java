package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {

    void register(String mail, String password) throws RegistrationException;

    User login(String login, String password) throws AuthenticationException;
}
