package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User create(User user);

    Optional<User> getByEmail(String email);

    List<User> getAll();
}
