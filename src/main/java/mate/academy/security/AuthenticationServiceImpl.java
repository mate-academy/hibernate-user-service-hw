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
        return userService.findByEmail(email)
                .filter(user -> {
                    String hashPassword = HashUtil.hashedPassword(password, user.getSalt());
                    return user.getPassword().equals(hashPassword);
                })
                .orElseThrow(() -> new AuthenticationException("Authentication failed. "
                        + "Login not found or wrong password."));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Please fill all fields");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
