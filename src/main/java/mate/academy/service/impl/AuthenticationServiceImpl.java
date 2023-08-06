package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserService;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HibernateUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty() && isPasswordValid(userOptional.get(), password)) {
            throw new AuthenticationException("Can not authenticate user");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException(
                    "Can not register new user. User with email " + email + " already exists");
        }
        User uregisteredUser = new User();
        uregisteredUser.setEmail(email);
        uregisteredUser.setPassword(password);
        return uregisteredUser;
    }

    private boolean isPasswordValid(User user, String password) {
        String hashedPassword = HibernateUtil.hashPassword(user.getPassword(), user.getSalt());
        return hashedPassword.equals(password);
    }
}
