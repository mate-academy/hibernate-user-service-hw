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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()
                || !userOptional.get().getPassword().equals(
                        HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!validateEmail(email) || !validatePassword(password)) {
            throw new RegistrationException("Can't register user");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean validateEmail(String email) {
        if (!email.contains("@")
                || email.isEmpty()
                || userService.findByEmail(email).isPresent()) {
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty() || password == null) {
            return false;
        }
        return true;
    }
}
