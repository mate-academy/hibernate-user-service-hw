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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("User email or password is incorrect");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("User email or password is incorrect");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("User with this email is already registered");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password can not be empty");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
