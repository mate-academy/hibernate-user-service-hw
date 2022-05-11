package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        User user = userFromDb.get();
        if (!email.contains("@") || password.isEmpty()
                || userFromDb.isEmpty()
                || !user.getPassword().equals(HashUtil.hashPassword(password,user.getSalt()))) {
            throw new AuthenticationException("Can`t login by fields: email = "
                    + email + " password = " + password);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!email.contains("@") || password.isEmpty()) {
            throw new RegistrationException("Can`t register by fields: email = "
                    + email + " password = " + password);
        }
        return userService.add(new User(email,password));
    }
}
