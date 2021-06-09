package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        throw new AuthenticationException("User is not found.");
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new AuthenticationException("User with email: " + email
                + " is already exists in DB");
    }
}
