package mate.academy.security;

import mate.academy.model.User;

public interface AuthentificationService {

    void register(String email, String password);

    User login(String email, String password);

}
