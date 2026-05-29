package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User save(User user);

    User get(int id);

    Optional<User> findByEmail(String email);
}
