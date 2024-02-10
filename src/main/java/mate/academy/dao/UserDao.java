package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    public User add(User user);

    public Optional<User> findByEmail(String email);
}
