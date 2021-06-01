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
        Optional<User> userFromDb = userService.findByLogin(email);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Username or password is incorrect");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        if (userService.findByLogin(email).isPresent()) {
            throw new AuthenticationException("Username - " + email
                    + " is already taken. Please choose another one");
        }
        return userService.add(new User(email, password));
    }
}
