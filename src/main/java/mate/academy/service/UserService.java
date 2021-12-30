package mate.academy.service;

import mate.academy.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByEmail(String email);
}
