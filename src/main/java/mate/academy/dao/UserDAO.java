package mate.academy.dao;

import mate.academy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User add(User user);

    Optional<User> get(Long id);

    List<User> all();
}
