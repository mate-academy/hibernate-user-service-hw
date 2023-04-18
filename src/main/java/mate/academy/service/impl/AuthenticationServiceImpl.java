package mate.academy.service.impl;

import java.util.NoSuchElementException;
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
        if (isNullOrEmpty(email, password)) {
            throw new AuthenticationException("Email or password shouldn't be null or empty");
        }
        Optional<User> user = userService.findByEmail(email);
        if (authenticate(user, password)) {
            throw new AuthenticationException("Email or password is invalid");
        }
        return userService.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("Can't get user by email: " + email));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (isNullOrEmpty(email, password)) {
            throw new RegistrationException("You must fill all the fields");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with such an email already exists");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean isNullOrEmpty(String email, String password) {
        return email == null || password == null || email.isEmpty()
                || password.isEmpty();
    }

    private boolean authenticate(Optional<User> user, String password) {
        return user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()));
    }
}
