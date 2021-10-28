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
        if (password.isEmpty() || userByEmail.isEmpty()
                || !HashUtil.encryptPassword(password, userByEmail.get()
                .getSalt()).equals(userByEmail.get().getPassword())) {
            throw new AuthenticationException("Can't authenticate user,"
                    + " email or password is not valid");
        }
        return userByEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with"
                    + " this email already exist or password was incorrect");
        }
        return userService.add(new User(email, password));
    }
}
