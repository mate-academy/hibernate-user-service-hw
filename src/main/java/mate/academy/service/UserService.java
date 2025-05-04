package mate.academy.service;

import java.util.Optional;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.User;

public interface UserService {
    User add(User user);

    User get(Long id) throws EntityNotFoundException;

    Optional<User> get(String email);
}
