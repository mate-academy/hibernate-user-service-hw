package mate.academy.auth;

import mate.academy.model.User;

public interface AuthenticationService {
    User login(String email, String password);

    User register(String email, String password);
}
