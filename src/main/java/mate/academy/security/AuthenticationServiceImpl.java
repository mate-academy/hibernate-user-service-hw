package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("This email is not registered");
        }
        User user = userOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (isValidPassword(hashedPassword, user.getPassword())) {
            return user;
        } else {
            throw new AuthenticationException("Invalid password");
        }

    }

    @Override
    public User register(String email, String password, String passwordAgain)
            throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("Email already exists");
        } else if (!isValidPassword(password, passwordAgain)) {
            throw new RegistrationException("The passwords do not match");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setSalt(HashUtil.getSalt());
        return userService.add(user);
    }

    private boolean isValidPassword(String inputPassword, String userPassword) {
        return inputPassword.equals(userPassword);
    }
}
