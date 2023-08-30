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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can't authentication user by email " + email);
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Wrong password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromBb = userService.findByEmail(email);
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Invalid password or email");
        }
        if (userFromBb.isPresent()) {
            throw new RegistrationException("User with current email " + email
                    + " is already registered");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));
        return userService.add(newUser);
    }
}
