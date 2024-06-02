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
        User user = optionalUserFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (password != null
                && user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("User with this email or password is not registered");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        if (email == null || password == null) {
            throw new RegistrationException("Email or password can't be null");
        }
        if (optionalUserByEmail.isPresent()) {
            throw new RegistrationException("The user with current email already exist");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
