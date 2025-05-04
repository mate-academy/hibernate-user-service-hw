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
        try {
            return userService.add(new User(email, password));
        } catch (Exception e) {
            throw new RegistrationException("Can't register new user by email "
                    + email, e);
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && checkPassword(password, userOptional.get())) {
            return userOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user. "
                + "Incorrect password or email.");
    }

    private boolean checkPassword(String password, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
