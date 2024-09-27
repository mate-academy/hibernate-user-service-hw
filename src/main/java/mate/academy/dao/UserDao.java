package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    Optional<User> get(String email);

    User add(User session);
}
