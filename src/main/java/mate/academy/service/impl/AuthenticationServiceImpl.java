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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()
                && userFromDbOptional.get().getPassword()
                        .equals(HashUtil.hashPassword(password, userFromDbOptional.get()
                                .getSalt()))) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!email.isEmpty() && !password.isEmpty()) {
            if (userService.findByEmail(email).isEmpty()) {
                User user = new User();
                user.setEmail(email);
                user.setSalt(HashUtil.getSalt());
                user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
                userService.add(user);
                return user;
            } else {
                throw new RegistrationException("The user with this email already exists");
            }
        } else {
            throw new RegistrationException("Email and password must not be empty");
        }
    }
}
