package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.model.User;

public interface UserService {

    User add(User user);

    User findById(Long id);

    User findByEmail(String email);

    Optional<User> findByLogin(String login);

    List<User> getAll();
}
