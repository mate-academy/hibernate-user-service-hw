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
    public User login(String email, String password) throws AuthenticationException {
        if (userService.findByEmail(email).isEmpty()) {
            throw new AuthenticationException("User with email: " + email + " was not found.");
        }
        User user = userService.findByEmail(email).get();
        boolean passwordIsCorrect = HashUtil.hashPassword(password, user.getSalt())
                    .equals(user.getPassword());
        if (passwordIsCorrect) {
            System.out.println("User: " + user.getEmail() + " was successfully logged in.");
            return user;
        }
        throw new AuthenticationException("Can`t log in user: "
                + email + ". Incorrect password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " already registered.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
