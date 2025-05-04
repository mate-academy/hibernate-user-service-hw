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
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can not authenticate user with such email: "
                    + email);
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password,user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can not authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email != null && password != null) {
            if (userService.findByEmail(email).isPresent()) {
                throw new RegistrationException("User with such email already exists");
            }
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            return userService.add(newUser);
        }
        throw new RegistrationException("Can not register new user with such email: "
                + email + " password:" + password);
    }
}
