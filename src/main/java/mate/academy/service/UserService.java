package mate.academy.service;

import mate.academy.model.User;

public interface UserService {

    User add(User user);

    User findByEmail(String email);
}
