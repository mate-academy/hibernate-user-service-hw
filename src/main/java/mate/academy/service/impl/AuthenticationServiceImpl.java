package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.PasswordUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty() || !PasswordUtil.verify(password, user.get().getHashedPassword())) {
            throw new AuthenticationException("Wrong password or email!");
        }

        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty()) {
            throw new RegistrationException("Email is empty");
        }

        if (password.isEmpty()) {
            throw new RegistrationException("Password is empty");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email Already Exists");
        }

        return new User(email, password);
    }
}
