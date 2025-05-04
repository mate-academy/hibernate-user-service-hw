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
    private static UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent()) {
            User user = userByEmailOptional.get();
            String hashPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }
}
