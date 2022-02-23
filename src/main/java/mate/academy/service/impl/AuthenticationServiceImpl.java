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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);

        if (userByEmailOptional.isPresent()) {
            User userByEmailFromDB = userByEmailOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, userByEmailFromDB.getSalt());

            if (userByEmailFromDB.getPassword().equals(hashedPassword)) {
                return userByEmailFromDB;
            }
        }
        throw new AuthenticationException("Authentication error. Email or password is incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);

        if (userByEmailOptional.isPresent()) {
            throw new RegistrationException("Email is already in use.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
