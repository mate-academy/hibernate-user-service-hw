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
        User userByEmail = userService.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("Email is invalid"));
        String hashPassword = HashUtil.hashPassword(password, userByEmail.getSalt());
        if (hashPassword.equals(userByEmail.getPassword())) {
            return userByEmail;
        }
        throw new AuthenticationException("Password is invalid");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "The user with email: " + email + " is already existing");
        }
        User userToRegister = new User();
        userToRegister.setEmail(email);
        userToRegister.setPassword(password);
        return userService.add(userToRegister);
    }
}
