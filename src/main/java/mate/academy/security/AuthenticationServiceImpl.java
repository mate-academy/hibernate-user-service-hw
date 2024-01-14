package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.service.impl.UserServiceImpl;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    /**
     * We should register a new user. The new user entity will contain the email and password
     *
     * @param email    - user email. should be unique for each user
     * @param password - user password
     * @return new user instance
     */
    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Failed to login with given email and password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Failed to register,"
                    + " user with given email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user); // #add method is automatically hashing the password
    }
}
