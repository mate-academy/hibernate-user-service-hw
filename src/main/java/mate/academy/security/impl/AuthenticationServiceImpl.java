package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        String actualPassword = HashUtil.hashPassword(password, userByEmail.get().getSalt());
        if (actualPassword.equals(userByEmail.get().getPassword())) {
            return userByEmail.get();
        } else {
            throw new AuthenticationException("Wrong password for email" + email);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try {
            byte[] salt = HashUtil.getSalt();
            String hashedPassword = HashUtil.hashPassword(password, salt);
            return userService.add(new User(email, hashedPassword, salt));
        } catch (Exception e) {
            throw new RegistrationException("Can't register new user with email " + email, e);
        }
    }
}
