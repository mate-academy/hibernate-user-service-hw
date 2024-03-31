package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authentication user");
    }

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("This email already exists : "
                    + email);
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password must contain more characters");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userService.add(user);
    }
}
