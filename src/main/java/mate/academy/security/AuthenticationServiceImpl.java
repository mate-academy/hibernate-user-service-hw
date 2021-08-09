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
        if (userFromDbOptional.isEmpty() || !(userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt())))) {
            throw new AuthenticationException("Can't find user by email: " + email
                    + " or your password is incorrect");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register, user with such email"
                    + " already exists " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
