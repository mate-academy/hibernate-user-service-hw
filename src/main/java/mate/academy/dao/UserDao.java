package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User movie);

    Optional<User> get(Long id);

    Optional<User> findByEmail(String email);
}
