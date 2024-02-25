package mate.academy.security;

import mate.academy.model.User;

public interface AuthenticationService {
    public void register(String email, String password);

    User login(String login, String password);
}
