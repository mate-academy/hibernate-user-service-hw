package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {

    User save(User user);

    Optional<User> getById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String email);
}
