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
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + ", already exist!");
        }
        if (password == null || email == null) {
            throw new RegistrationException("Password or email can not be empty!");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(login);
        if (optionalUser.isEmpty() || !HashUtil.hashPassword(password,
                optionalUser.get().getSalt()).equals(optionalUser.get().getPassword())) {
            throw new AuthenticationException("Wrong email or password!");
        }
        return optionalUser.get();
    }
}
