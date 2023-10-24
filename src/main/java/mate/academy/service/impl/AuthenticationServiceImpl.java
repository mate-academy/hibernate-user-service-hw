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
        User userFromDB = userService.findByEmail(email).orElseThrow(() -> 
                new AuthenticationException("Can't find user by this email: " + email));
        String userFromDbPassword = userFromDB.getPassword();
        String currentUserHashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        if (currentUserHashedPassword.equals(userFromDbPassword)) {
            return userFromDB;
        } 
        throw new AuthenticationException("Your password not match. Try again.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Unable to register a new user with this "
                    + "email address: " + email
                    + ". It has already been used for registration");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userService.add(user);
        return user;
    }
}
