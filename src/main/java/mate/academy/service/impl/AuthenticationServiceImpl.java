package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtill;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String mail, String password) {
        Optional<User> currentUser = userService.findByEmail(mail);
        if (currentUser.isEmpty() || !currentUser.get().getPassword()
                .equals(HashUtill.hashPassword(password, currentUser.get().getSalt()))) {
            throw new AuthenticationException("User not found :( ");
        }
        return currentUser.get();
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new AuthenticationException("User already exist");
    }
}
