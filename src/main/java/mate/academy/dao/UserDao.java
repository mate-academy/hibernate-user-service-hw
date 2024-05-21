package mate.academy.dao;

import java.util.Optional;
import java.util.Set;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    Set<String> getAllLogins();

    Optional<User> findByEmail(String login);
}
