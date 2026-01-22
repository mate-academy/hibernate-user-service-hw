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
    public User register(String email,String password) {
        if (userService.findByEmail(email).isPresent()) {
            try {
                throw new RegistrationException("User with email " + email + " already exists");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }
        User user = new User();
        user.setEmail(email);
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(password, salt));
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            String hashedPassword = HashUtil.hashPassword(password, userFromDb.get().getSalt());
            if (hashedPassword.equals(userFromDb.get().getPassword())) {
                return userFromDb.get();
            }
        }
        try {
            throw new AuthenticationException("Invalid email or password");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
