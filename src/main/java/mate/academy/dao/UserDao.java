package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    Optional<User> get(Long id);

    Optional<User> findByEmail(String email);

    User add(User user);
}
