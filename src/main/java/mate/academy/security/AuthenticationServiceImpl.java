package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && userFromDb.get().getPassword().equals(password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can't login. Login or password is incorrect ");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
            if (userFromDb.isPresent()) {
                throw new RegistrationException("This email address is already being used ");
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
    }
}
