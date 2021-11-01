package mate.academy.dao;

import java.util.Optional;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface UserDao {
    User add(User user) throws RegistrationException;

    Optional<User> getByEmail(String email);
}
