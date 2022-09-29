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
    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (checkData(userOptional, email, password)) {
            throw new AuthenticationException("Email or password are incorrect.");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists.");
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new RegistrationException("The mail is not valid. Adjust its appearance.");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("The password must be at least one character.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean checkData(Optional<User> userOptional, String email, String password) {
        return userOptional.isEmpty() || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()));
    }
}
