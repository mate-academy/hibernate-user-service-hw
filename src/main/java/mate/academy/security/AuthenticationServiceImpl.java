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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, currentUser.getSalt());
            if (currentUser.getPassword().equals(hashedPassword)) {
                return currentUser;
            }
        }
        throw new AuthenticationException("Can't authenticate User with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("User with email " + email + "is already registered");
        }
        return userService.add(new User(email, password));
    }
}
