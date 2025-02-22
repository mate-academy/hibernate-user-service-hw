package mate.academy.security;

import java.util.Optional;
import javax.naming.AuthenticationException;
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
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new AuthenticationException("Email or password can't be empty");
        }
        Optional<User> optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isPresent()) {
            throw new AuthenticationException("User with this email already exists");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.add(newUser);
        return newUser;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isEmpty()) {
            throw new RegistrationException("Can't authenticate user");
        }
        User user = optionalUserFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new RegistrationException("Can't authenticate user");
    }
}
