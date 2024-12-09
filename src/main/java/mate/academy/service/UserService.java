package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email);

    User get(Long id);

    List<User> getAll();
}
