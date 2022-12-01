package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserService extends AbstractService<User> {
    Optional<User> findByEmail(String email);

    List<User> getAll();
}
