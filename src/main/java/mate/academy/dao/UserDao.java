package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    Optional<User> get(Long id);

    List<User> getAll();

    User add(User user);

    Optional<User> findByEmail(String email);
}
