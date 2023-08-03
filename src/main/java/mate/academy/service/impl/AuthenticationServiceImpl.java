package mate.academy.service.impl;

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
    public User login(String email, String password)
            throws AuthenticationException, NoSuchFieldException {
        if (userService.findByEmail(email).isPresent() && !(password.isEmpty())) {
            try {
                User user = userService.findByEmail(email).get();
                user.getPassword().equals(HashUtil.hasPassword(password, user.getSalt()));
                return user;
            } catch (Exception e) {
                throw new AuthenticationException("The password or login is invalid", e);
            }
        }
        throw new NoSuchFieldException("Enter your login and password");
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException, NoSuchFieldException {
        if (!(email.isEmpty() && password.isEmpty())) {
            try {
                userService.findByEmail(email).isEmpty();
                User user = new User(email, password);
                userService.add(user);
                return user;
            } catch (Exception e) {
                throw new RegistrationException("User already exists", e);
            }
        }
        throw new NoSuchFieldException("Enter your login and password");
    }
}
