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
        Optional<User> userFromDb = userService.findByEmail(email);
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.orElseThrow().getSalt());
        if ((!password.isEmpty())
                || isValidPassword(userFromDb.orElseThrow().getPassword(), hashedPassword)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect email or password!");
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Enter all data");
        } else if (userOptional.isPresent()) {
            throw new RegistrationException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setSalt(HashUtil.getSalt());
        return userService.add(user);
    }

    private boolean isValidPassword(String inputPassword, String userPassword) {
        return inputPassword.equals(userPassword);
    }
}
