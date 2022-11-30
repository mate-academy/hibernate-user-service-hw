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
        if (!userOptional.isEmpty()) {
            User user = userOptional.get();
            String expectedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(expectedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't login. Username or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.save(new User(email, password));
        }
        throw new RegistrationException("User with this email is already exist");
    }
}
