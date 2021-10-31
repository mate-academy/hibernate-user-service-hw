package mate.academy.service;

import mate.academy.model.User;

public interface AuthenticationService {
    User register(String login, String password);

    User login(String login, String password);
}
