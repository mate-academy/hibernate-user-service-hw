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
        Optional<User> optionalUser = userService.findByEmail(email);
        boolean correctPassword = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String receivedPassword = HashUtil.hashPassword(password, user.getSalt());
            correctPassword = receivedPassword.equals(user.getPassword());
        }
        if (optionalUser.isEmpty() || !correctPassword) {
            throw new AuthenticationException("There "
                    + "is no user with such an email or wrong password!");
        }
        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with such email already present");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.add(user);
    }
}
