package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    public User save(User movie);

    public User get(Long id);

    Optional<User> findByLogin(String login);
}
