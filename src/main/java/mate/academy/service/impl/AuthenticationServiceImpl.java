package mate.academy.service.impl;

import java.util.Objects;
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
        User user = optionalUser.orElse(null);
        if (user == null || !Objects.equals(user.getPassword(),
                HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Invalid email - password combination");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with such email already registered. "
                    + "Email: " + email);
        }
        return userService.add(new User(email, password));
    }
}
