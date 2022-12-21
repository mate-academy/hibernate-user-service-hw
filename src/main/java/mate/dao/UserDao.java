package mate.dao;

import java.util.List;
import java.util.Optional;
import mate.model.User;

public interface UserDao {
    User add(User user);

    Optional<User> get(Long id);

    List<User> getAll();

    Optional<User> findByEmail(String email);
}
