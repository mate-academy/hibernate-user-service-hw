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
    public User login(String email, String password) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String inputPassword = HashUtil.hashPassword(password, user.getSalt());
            if (inputPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Wrong email or password!");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
            return userService.add(user);
        } else {
            throw new RegistrationException("User with email: " + email + " already exist!");
        }

    }
}
