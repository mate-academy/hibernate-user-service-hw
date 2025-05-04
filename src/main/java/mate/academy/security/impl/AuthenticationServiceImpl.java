package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.orElseThrow(() ->
                new RuntimeException("Can't get user from userFromDbOptional"));
        String userPasswordFromDb = user.getPassword();
        String inputPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userPasswordFromDb.equals(inputPassword)) {
            return user;
        }
        throw new AuthenticationException("Password or email is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("There is already user with such email");
        }

        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException(
                    "password length or email length must be greater than 0");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
