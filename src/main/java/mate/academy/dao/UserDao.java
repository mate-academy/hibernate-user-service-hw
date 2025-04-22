package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    Optional<User> get(String email);

    User update(User user);

    boolean delete(String email);
}
