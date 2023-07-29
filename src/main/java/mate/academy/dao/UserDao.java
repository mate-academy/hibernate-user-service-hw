package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    User get(Long id);

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email); // we will use this `Optional` later
}
