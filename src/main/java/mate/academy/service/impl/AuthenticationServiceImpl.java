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
        AuthenticationException exception =
                new AuthenticationException("Login or password is incorrect");
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() &&
                userOptional.get().getPassword().equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            return userOptional.get();
        }
        throw exception;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        byte[] salt = new byte[2];
        salt[0] = 6;
        salt[1] = 12;
        User toAdd = new User(
                email,
                HashUtil.hashPassword(password,salt),
                salt
        );
        userService.add(toAdd);
        return toAdd;
    }
}
