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
    public void register(String email, String password) throws RegistrationException {
        User user = new User(email, password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists: " + email);
        }
        userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.stringPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user by email: " + email);
        }
        return userFromDb.get();
    }
}
