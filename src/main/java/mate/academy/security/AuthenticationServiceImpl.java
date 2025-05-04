package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
     private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isEmpty()) {
            throw new AuthenticationException("No users with email: " + email);
        }
        User user = userByEmailOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null) {
            throw new RegistrationException("Couldn't register a new user");
        }
        User user = new User();
        byte[] salt = HashUtil.getSalt();
        user.setPassword(HashUtil.hashPassword(password,salt));
        user.setEmail(email);
        return userService.add(user);
    }
}
