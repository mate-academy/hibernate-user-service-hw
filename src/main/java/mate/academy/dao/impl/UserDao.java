package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    Optional<User> findByEmail(String email);

}
