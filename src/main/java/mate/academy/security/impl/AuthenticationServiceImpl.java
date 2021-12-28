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
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent()
                && userByEmailOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userByEmailOptional.get().getSalt()))) {
            return userByEmailOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!password.isEmpty() && userService.findByEmail(email).isEmpty()) {
            User userToRegister = new User();
            userToRegister.setEmail(email);
            userToRegister.setPassword(password);
            return userService.add(userToRegister);
        }
        throw new RegistrationException("Can't register user by e-mail " + email);
    }
}
