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
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !isValidPassword(user.get(),password)) {
            throw new AuthenticationException("Can't authenticate user by email: " + email);
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateRegisterData(email,password);
        return userService.add(new User(email, password));
    }

    private void validateRegisterData(String email, String password) throws RegistrationException {
        if (email.isEmpty() || email.isBlank()
                || password.isEmpty() || password.isBlank()) {
            throw new RegistrationException("Email and password can't be empty!");
        } else if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exist");
        }
    }

    private boolean isValidPassword(User user, String password) {
        return password != null && user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
