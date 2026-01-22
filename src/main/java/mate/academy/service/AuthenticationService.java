package mate.academy.service;

import mate.academy.model.User;

public interface AuthenticationService {
    public User register(String email, String password);

    public User login(String email, String password);

}
