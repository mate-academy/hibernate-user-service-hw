package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        Optional<User> userEmailFromDB = userService.findByEmail(email);
        if (userEmailFromDB.isPresent()
                && userEmailFromDB.get().getPassword()
                .equals(HashUtil.hashPassword(password, userEmailFromDB.get().getSalt()))) {
            return userEmailFromDB.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        } else {
            throw new RuntimeException("Can't register new user."
                    + " Email already exist. Email: " + email);
        }
    }
}
