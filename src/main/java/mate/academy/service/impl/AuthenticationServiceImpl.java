package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.HashUtil;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl
        implements AuthenticationService {

    @Inject
    private UserService userService;

    public AuthenticationServiceImpl() {
    }

    @Override
    public User login(String email, String password)
            throws AuthenticationException {

        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()
                || !optionalUser.get().getPassword().equals(
                HashUtil.hashPassword(
                        password,
                        optionalUser.get().getSalt()))) {
            throw new AuthenticationException(
                    "Incorrect email or password");
        }

        return optionalUser.get();
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "User with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }
}
