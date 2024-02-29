package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
    public void register(String email, String password) throws RegistrationException {
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException(
                    "Can't register new user. Possible cause: user with email " + email
                            + " already exists");
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);

        userFromDbOptional.filter(user -> {
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            return user.getPassword().equals(hashedPassword);
        }).orElseThrow(() -> new AuthenticationException(
                "Can't authenticate user: invalid email or password. Try again..."));

        return userFromDbOptional.get();
    }

}
