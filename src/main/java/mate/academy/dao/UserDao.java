package mate.academy.dao;

import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.User;

@Dao
public interface UserDao {
    User save(User user);

    User get(Long id);

    Optional<User> findByEmail(String email);
}
