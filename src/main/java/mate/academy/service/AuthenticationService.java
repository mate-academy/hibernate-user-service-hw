package mate.academy.service;

import mate.academy.model.User;

public interface AuthenticationService {
    User login(String mail, String password);

    User register(String email, String password);
}
