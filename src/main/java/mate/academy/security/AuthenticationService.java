package mate.academy.security;

import mate.academy.model.User;

public interface AuthenticationService {
    User register(String login, String password);

    User login(String login, String password);

    User authenticate(String login, String password);
}
