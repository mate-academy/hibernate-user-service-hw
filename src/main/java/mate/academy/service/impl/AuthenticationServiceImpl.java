package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
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
        try {
            Optional<User> userFromDB = userService.findByEmail(email);
            if (userFromDB.isEmpty()) {
                throw new AuthenticationException("Can't authenticate user!");
            }
            User user = userFromDB.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        } catch (DataProcessingException e) {
            throw new AuthenticationException(
                    "Can't authorise user with email: " + email + " , error: " + e);
        }
        throw new AuthenticationException("Can't authenticate user!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty()) {
            throw new RegistrationException("User email cannot be empty!");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("User password cannot be empty!");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
