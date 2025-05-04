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
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Can't login user");
        }

        User user = optionalUser.get();
        String hashedPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }

        throw new AuthenticationException("Wrong password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        User user = new User(email, password);
        userService.add(user);
        return user;
    }
}
