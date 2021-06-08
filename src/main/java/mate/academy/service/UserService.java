package mate.academy.service;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.model.User;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email) throws AuthenticationException;
}
