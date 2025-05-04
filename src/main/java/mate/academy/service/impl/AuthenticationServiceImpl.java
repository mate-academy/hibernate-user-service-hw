package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()
                || !userFromDb.get().getPassword().equals(password)) {
            throw new AuthenticationException("Login or password was incorrect");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Please check the entered data. "
                    + " The password cannot be empty or a user"
                    + " with such an email is already registered.");
        }
        return userService.add(new User(email,password));
    }
}
