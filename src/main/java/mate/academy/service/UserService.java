package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User save(User user);

    Optional<User> findUserByEmail(String email);
}
