package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent() && matchPasswords(password, userByEmail.get())) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Username or password not found");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(password, email);
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(user);
        }
        throw new RegistrationException("Can`t register user with email: " + email);
    }

    private boolean matchPasswords(String rawPassword, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
