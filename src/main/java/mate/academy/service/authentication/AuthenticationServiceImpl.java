package mate.academy.service.authentication;

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
        Optional<User> optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isEmpty()) {
            throw new AuthenticationException("Can't authentication user with email: " + email);
        }
        User user = optionalUserFromDB.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authentication user with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isPresent()) {
            throw new RegistrationException("Can't register user with email: " + email);
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
