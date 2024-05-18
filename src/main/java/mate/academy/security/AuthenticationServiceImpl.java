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
        Optional<User> optionalUserFromDb = userService.findByEmail(email);
        if (optionalUserFromDb.isEmpty()) {
            throw new AuthenticationException("User with this email is not registered");
        }
        User user = optionalUserFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("User with this email or password is not registered");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email, password);
        return userService.add(user);
    }
}
