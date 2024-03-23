package mate.academy.service;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;

    /**
     * We should register a new user. The new user entity will contain the email and password
     * @param login - user email. should be unique for each user
     * @param password - user password
     * @return new user instance
     */
    User register(String login, String password) throws RegistrationException;
}
