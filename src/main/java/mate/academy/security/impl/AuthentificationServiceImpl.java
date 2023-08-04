package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthentificationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {
    private static final String ERROR_MESSAGE = "Wrong login or password for email ";
    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password is empty");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        System.out.println("User with email " + email + " was registered");
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).orElseThrow(
                () -> new AuthenticationException(ERROR_MESSAGE + email));
        if (!user.getPassword().equals(
                HashUtil.hashPassword(password, user.getSalt()))) {
            new AuthenticationException(ERROR_MESSAGE + email);
        }
        return user;
    }

}
