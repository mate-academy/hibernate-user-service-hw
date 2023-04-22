package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;

public interface UserService {
    User add(User user) throws RegistrationException, AuthenticationException;

    User getUser(Long id);

    List<User> getAll();

    Optional<User> findByEmail(String email);
}
