package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDAO {
    User add(User user);

    Optional<User> findByEmail(String email);
}
