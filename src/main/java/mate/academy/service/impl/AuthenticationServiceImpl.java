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
        Optional<User> userFromDB = userService.findByEmail(email);
        if (isEmpty(email, password)
                || userFromDB.isEmpty()
                || !HashUtil.hashPassword(password, userFromDB.get().getSalt())
                .equals(userFromDB.get().getPassword())) {
            throw new AuthenticationException("Invalid email and/or password.");
        }
        return userFromDB.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (isEmpty(email, password) || userFromDB.isPresent()) {
            throw new RegistrationException("User with this email already exist!");
        }
        User userForRegistration = new User();
        userForRegistration.setEmail(email);
        userForRegistration.setPassword(password);
        return userService.add(userForRegistration);
    }

    private boolean isEmpty(String email, String password) {
        return email.isEmpty() || password.isEmpty();
    }
}
