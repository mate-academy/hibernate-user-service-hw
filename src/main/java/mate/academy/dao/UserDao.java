package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    public boolean checkLoginExists(String login);

    Optional<User> findByEmail(String login);
}
