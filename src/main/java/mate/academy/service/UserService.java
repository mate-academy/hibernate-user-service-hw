package mate.academy.service;

import java.util.List;
import java.util.Optional;

import mate.academy.model.User;

public interface UserService {

    User add(User user);

    User findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> getAll();
}
