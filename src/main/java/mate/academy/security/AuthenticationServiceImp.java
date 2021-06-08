package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegisterException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Email is empty");
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authentication user by email" + email);
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegisterException("User was yet");
    }
}
