package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.UserAuthenticationException;
import mate.academy.exception.UserRegistrationException;
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
    public User login(String email, String password) throws UserAuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, optionalUser.get().getSalt()))) {
            return optionalUser.get();
        }
        throw new UserAuthenticationException("email or password are incorrect");
    }

    @Override
    public User register(String email, String password) throws UserRegistrationException {
        if (validateLogin(email) || validatePassword(password)) {
            throw new UserRegistrationException("Incorrect email or password");
        }
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserRegistrationException("There is already an email in DB:");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean validateLogin(String login) {
        return login == null || login.isEmpty();
    }

    private boolean validatePassword(String password) {
        return password == null || password.isEmpty();
    }
}
