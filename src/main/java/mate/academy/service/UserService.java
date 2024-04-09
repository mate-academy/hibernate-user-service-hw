package mate.academy.service;

import java.util.Optional;
import mate.academy.lib.Service;
import mate.academy.model.User;

@Service
public interface UserService {

    User add(User user);

    Optional<User> findByEmail(String email);
}
