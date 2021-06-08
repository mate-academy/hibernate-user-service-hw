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
        Optional<User> userFoundByEmail = userService.findByEmail(email);
        if (userFoundByEmail.isPresent()
                && userFoundByEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFoundByEmail.get().getSalt()))) {
            return userFoundByEmail.get();
        }
        throw new AuthenticationException("Can not authenticate user with email = " + email);
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new AuthenticationException("User with this email already exist. "
                    + "Email = " + email);
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
