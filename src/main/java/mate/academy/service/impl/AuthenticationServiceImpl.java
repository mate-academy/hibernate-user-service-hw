package mate.academy.service.impl;

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
        try {
            User user = userService.findByEmail(email).get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (!hashedPassword.equals(user.getPassword())) {
                throw new RuntimeException();
            }
            return user;
        } catch (Exception e) {
            throw new AuthenticationException(("Can't authenticate user with email " + email));
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email " + email
                    + ". This user already exist");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.add(user);
    }
}
