package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    Optional<User> add(Long id);

    Optional<User> findByEmail(String email);
}
