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
        if (userOptional.isPresent() && validatePassword(userOptional.get(), password)) {
            return userOptional.get();
        }
        throw new AuthenticationException(String
                .format("Login failure with email {%s}!", email));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null
                || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Registration failure. Login or password is empty!");
        }
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException(String
                    .format("Registration failure. "
                            + "User with email {%s} already exists!", email));
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }

    private boolean validatePassword(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
