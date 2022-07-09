package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User create(User user);
    Optional<User> findByEmail(String email);
}
