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
        if (userOptional.isPresent() && passwordIsValid(userOptional.get(), password)) {
            return userOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user with e-mail " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.matches("^^(\\w+\\.?)+\\w+@([\\w]+\\.?)+\\w+$")
                && password.matches("^\\w{7,30}$")) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("Can't register user with e-mail " + email);
    }

    private boolean passwordIsValid(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
