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
    public User login(String email, String password) {
        try {
            Optional<User> userByEmail = userService.findByEmail(email);
            if (userByEmail.isEmpty()) {
                throw new AuthenticationException("Can't authenticate user by this email: "
                        + userByEmail);
            }
            User user = userByEmail.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
            throw new AuthenticationException("Wrong password for this email: " + email);
        } catch (AuthenticationException e) {
            throw new RuntimeException("login are failed by this email " + email, e);
        }
    }

    @Override
    public User register(String email, String password) {
        try {
            if (userService.findByEmail(email).isPresent()) {
                throw new RegistrationException("this email already exist " + email);
            }
            if (email.isEmpty() || password.isEmpty()) {
                throw new RegistrationException("Can't create by empty email or password");
            }
        } catch (RegistrationException e) {
            throw new RuntimeException("registration are failed by this email " + email, e);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
