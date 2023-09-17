package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("There is no user with such an email");
        }
        User user = optionalUser.get();
        String receivedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (receivedPassword.equals(user.getPassword())) {
            return user;
        } else {
            throw new AuthenticationException("Wrong password!");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with such email already present");
        }
        User user = new User();
        user.setSalt(HashUtil.getSalt());
        String newPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(newPassword);
        return userService.add(user);
    }
}
