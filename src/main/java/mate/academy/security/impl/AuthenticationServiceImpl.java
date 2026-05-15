package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.service.impl.UserServiceImpl;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService = new UserServiceImpl();

    public AuthenticationServiceImpl() {
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromObOptional = userService.findByEmail(email);
        User user = userFromObOptional
                .orElseThrow(() -> new AuthenticationException("User not found"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Invalid password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException("Password cannot be empty");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User addedUser = userService.add(user);
        if (addedUser == null) {
            throw new RegistrationException("Invalid email " + email);
        }
        return addedUser;
    }
}
