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
        Optional<User> currentUser = userService.findByEmail(email);
        if (currentUser.isEmpty()
                || !(HashUtil.hashPassword(password, currentUser.get().getSalt()))
                        .equals(currentUser.get().getPassword())) {
            throw new AuthenticationException("email or password incorrect");
        }
        return currentUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("user with email: " + email + " already exist");
    }
}
