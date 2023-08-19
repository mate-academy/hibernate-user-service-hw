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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()
                || !userFromDbOptional.get().getPassword().equals(HashUtil.hashPassword(password,
                userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Email or Password is incorrect");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        String exceptionText = "Can't register new User with email " + email
                + " and password " + "****";
        if (userService.findByEmail(email).isPresent() || password.isEmpty()) {
            throw new RegistrationException(exceptionText);
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.add(user);
    }
}
