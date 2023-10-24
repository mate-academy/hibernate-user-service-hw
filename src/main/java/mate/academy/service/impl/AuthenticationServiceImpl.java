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
        Optional<User> dbUser = userService.findByEmail(email);
        if (dbUser.isEmpty()
                || !dbUser.get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, dbUser.get().getSalt()))) {
            throw new AuthenticationException("Email address or the password is wrong");
        }
        return dbUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("The user with email " + email + " already exists");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password cannot be empty");
        }
        User user = new User();
        user.setEmail(email);
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(password, salt));
        return user;
    }
}
