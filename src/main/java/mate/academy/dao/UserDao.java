package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User save(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    void removeUser(User user);

    Optional<User> findByEmail(String email);
}
