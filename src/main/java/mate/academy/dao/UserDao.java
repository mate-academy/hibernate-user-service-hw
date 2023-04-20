package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {

    Optional<User> findByLogin(String login);

    User add(User user);
}
