package mate.academy.service;

import mate.academy.model.User;

public interface UserService {
    User save(User user);

    User findByLogin(String login);
}
