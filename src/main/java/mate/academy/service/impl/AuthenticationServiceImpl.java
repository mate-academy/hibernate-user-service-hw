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
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()
                && userByEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password, userByEmail.get().getSalt()))) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Email or password is wrong");
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User(email, password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("This email is already taken: " + email);
    }
}
