package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User add(User movie);

    Optional<User> findByEmail(String email);
}
