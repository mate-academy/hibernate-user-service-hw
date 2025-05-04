package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.EmailValidatorUtil;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String userPassword = user.getPassword();
            byte[] salt = user.getSalt();
            boolean isValidPassword = userPassword.equals(HashUtil.hashPassword(password, salt));
            if (!isValidPassword) {
                throw new AuthenticationException("Invalid password");
            }
            return user;
        }
        throw new AuthenticationException("User with email ->" + email + " not found");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email ->" + email + " already exists");
        }
        boolean isValidEmail = EmailValidatorUtil.isValidEmail(email);
        User newUser = null;
        if (isValidEmail) {
            newUser = new User(email, password);
        }
        return userService.add(newUser);
    }
}
