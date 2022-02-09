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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.get();
        if (user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()))) { //Использовать вариант1
            return user;
        }
        if (userFromDbOptional.isPresent()
                && userFromDbOptional.get().getPassword() // Использовать вариант 2
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Can't authentication user, invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty() && !password.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register user. User with email "
                + email + " already exist");
    }
}
