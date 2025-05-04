package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AlreadyExistingEmailException;
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
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()
                && optionalUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, optionalUser.get().getSalt()))) {
            return optionalUser.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws AlreadyExistingEmailException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new AlreadyExistingEmailException("User with such email: " + email
                + " has already been inserted to the DB");
    }
}
