package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
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
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && checkPassword(user.get(), password)) {
            return user.get();
        }
        throw new AuthenticationException("mail or password incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("This mail " + email + " is already taken");
        }
        return userService.add(new User(email, password));
    }

    private boolean checkPassword(User user, String password) {
        if (password.isEmpty()) {
            return false;
        }
        return HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword());
    }
}
