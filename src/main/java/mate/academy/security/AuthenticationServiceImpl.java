package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            User existingUser = user.get();
            byte[] salt = existingUser.getSalt();
            String hashedPassword = HashUtil.hashPassword(password, salt);
            if (hashedPassword.equals(existingUser.getPassword())) {
                return existingUser;
            }
        }
        System.err.println("Failed login attempt for email: " + email);
        throw new AuthenticationException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            System.err.println("Registration attempt with already used email: " + email);
            throw new RegistrationException("Email is already in use");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
