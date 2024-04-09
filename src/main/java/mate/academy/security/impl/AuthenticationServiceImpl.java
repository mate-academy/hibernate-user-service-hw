package mate.academy.security.impl;

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
        User userByEmail = userService.findByEmail(email).orElseThrow(()
                -> new AuthenticationException("There is no user with email " + email));
        String actualPassword = HashUtil.hashPassword(password, userByEmail.getSalt());
        if (actualPassword.equals(userByEmail.getPassword())) {
            return userByEmail;
        } else {
            throw new AuthenticationException("Wrong password for email" + email);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try {
            if (userService.findByEmail(email) != null) {
                throw new RegistrationException("This email is already in use " + email);
            }
            byte[] salt = HashUtil.getSalt();
            String hashedPassword = HashUtil.hashPassword(password, salt);
            return userService.add(new User(email, hashedPassword, salt));
        } catch (Exception e) {
            throw new RegistrationException("Can't register new user with email " + email, e);
        }
    }
}
