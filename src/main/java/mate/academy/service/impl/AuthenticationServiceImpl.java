package mate.academy.service.impl;

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
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(
                        "No user found for email " + email)
                );

        if (!HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isBlank() || email.isBlank()) {
            throw new RegistrationException("Either your password or your email is empty or blank");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with the current email already exists");
        }

        return userService.add(new User(email, password));
    }
}
