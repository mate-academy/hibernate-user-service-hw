package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    Optional<User> findByEmail(String email);

    User add(User session);
}
