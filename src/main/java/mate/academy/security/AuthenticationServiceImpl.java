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
        if (userFromDbOptional.isPresent()
                && isPasswordCorrect(userFromDbOptional.get(), password)) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Email or password are incorrect!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try {
            return userService.add(new User(email, password));
        } catch (Exception e) {
            throw new RegistrationException("Can't create a new user, "
                    + "maybe the email - registered yet! " + email, e);
        }
    }

    private boolean isPasswordCorrect(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
