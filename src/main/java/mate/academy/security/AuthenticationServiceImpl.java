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
    public User register(String email, String password) throws RegistrationException {
        return userService.add(new User(email, password));
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()
                && passwordValidate(password, userFromDbOptional.get())) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Can't register user by email: " + email);
    }

    private boolean passwordValidate(String password, User user) {
        return HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword());
    }
}
