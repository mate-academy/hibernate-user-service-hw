package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User add(User user);

    User get(Long id);

    Optional<User> findByEmail(String login);
}
