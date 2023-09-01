package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User add(User add);

    Optional<User> findByEmail(String email);
}
