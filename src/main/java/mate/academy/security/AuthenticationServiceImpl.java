package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.service.impl.UserServiceImpl;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService = new UserServiceImpl();

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email: " + email + " is incorrect");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password,
                        userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return userFromDb.get();
    }
}
