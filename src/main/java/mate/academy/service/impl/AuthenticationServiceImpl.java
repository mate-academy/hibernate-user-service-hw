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
        if (optionalUser.isPresent() && checkPassword(password, optionalUser)) {
            return optionalUser.get();
        }
        throw new AuthenticationException("User does not exist or incorrect data");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("User already exist");
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }

    private boolean checkPassword(String password, Optional<User> optionalUser) {
        String hashedPassword = HashUtil.getHash(password, optionalUser.get().getSalt());
        return hashedPassword.equals(optionalUser.get().getPassword());
    }
}
