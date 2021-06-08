package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
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
        Optional<User> currentUser = userService.findByEmail(email);
        if (currentUser.isPresent() && currentUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, currentUser.get().getSalt()))) {
            return currentUser.get();
        }
        throw new AuthenticationException("Can't authenticate user by email: " + email);
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            return userService.add(newUser);
        }
        throw new DataProcessingException("User with email: " + email + " already registered");
    }
}
