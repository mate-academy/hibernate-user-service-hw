package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    public User add(User user);

    public Optional<User> findByEmail(String email);
}
