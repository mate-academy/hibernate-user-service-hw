package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
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
        String hashedPassword = HashUtil.hashPassword(password, user.get().getSalt());

        if (user.isEmpty() || !hashedPassword.equals(user.get().getPassword())) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with email " + email + " is already exists");
        }

        User addedUser = userService.add(new User(email, password));
        return addedUser;
    }
}
