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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("A user with this email: "
                    + email + " does not exist");
        } else {
            User user = userOptional.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
            throw new AuthenticationException("Wrong password");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            byte[] salt = HashUtil.getSalt();
            User newUser = new User(email, HashUtil.hashPassword(password, salt));
            newUser.setSalt(salt);
            return userService.add(newUser);
        } else {
            throw new RegistrationException("A user with this email: "
                    + email + " already exists");
        }
    }
}
