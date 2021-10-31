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
    public User register(String email, String password) {
        return userService.add(new User(email, password));
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userByEmail = userService.getByEmail(email);
        if (userByEmail.isPresent()) {
            if (userByEmail.get().getPassword()
                    .equals(getPassword(password, userByEmail.get().getSalt()))) {
                return userByEmail.get();
            }
        }
        throw new AuthenticationException("Login or password was incorrect."
                + "Check login, password or register");
    }

    private String getPassword(String password, byte[] salt) {
        return HashUtil.getHashPassword(password, salt);
    }
}
