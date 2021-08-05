package mate.academy.service.impl;

import java.util.Objects;
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
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !Objects.equals(user.get().getPassword(),
                HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Could not authenticate User by email: " + email
                    + ". Email or Password is invalid!");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) {
        if (email == null || userService.findByEmail(email).isPresent()
                || password == null || password.length() == 0) {
            throw new RuntimeException("Incorrect email or password");
        }
        return userService.add(new User(email, password));
    }
}
