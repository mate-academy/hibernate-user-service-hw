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
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()
                || !HashUtil.hashPassword(password, userOptional.get().getSalt())
                    .equals(userOptional.get().getPassword())) {
            throw new AuthenticationException("Wrong password or user doesn't exist for email: "
                    + email);
        }

        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null
                || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password can't be empty!");
        }

        return userService.add(new User(email, password));
    }
}
