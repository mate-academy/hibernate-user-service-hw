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
    private static final int MIN_LENGTH_LOGIN = 3;
    private static final int MIN_LENGTH_PASSWORD = 8;

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        String hashedPassword = HashUtil.getHashPassword(password, userOptional.get().getSalt());
        if (!userOptional.isEmpty() || userOptional.get().getPassword().equals(hashedPassword)) {
            return userOptional.get();
        }
        throw new AuthenticationException("Login or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("There is already a registered user with this email "
                    + email);
        }
        if (email.isEmpty() || email.length() < MIN_LENGTH_LOGIN || !email.contains("@")) {
            throw new RegistrationException("Login can`t be empty or length over "
                    + MIN_LENGTH_LOGIN + ", or login contains @");
        }
        if (password.isEmpty() || password.length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Password must be over "
                    + MIN_LENGTH_PASSWORD + " symbols");
        }
        User user = new User(email, password);
        userService.add(user);
        return user;
    }
}
