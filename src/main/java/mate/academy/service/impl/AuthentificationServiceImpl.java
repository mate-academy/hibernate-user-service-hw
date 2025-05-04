package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthentificationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (!userFromDbOptional.isEmpty()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }

        throw new AuthenticationException("Email or password was incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("User with this email is already registered.");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        userService.add(user);
        return user;
    }
}
