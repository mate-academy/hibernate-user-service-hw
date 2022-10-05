package mate.academy.service;

import mate.academy.model.User;

public interface AuthenticationService {
    User login(String login, String password);

    User register(String login, String password);
}
