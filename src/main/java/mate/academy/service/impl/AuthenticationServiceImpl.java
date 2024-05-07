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
        if (user.isPresent() && checkPassword(password, user.get())) {
            return user.get();
        }
        throw new AuthenticationException("Can't authenticate user with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Password and Email are required");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This user is already exist with email: " + email);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }

    private boolean checkPassword(String password, User user) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
