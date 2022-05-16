package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.EncryptionUtil;
import mate.academy.util.ValidationUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword()
                .equals(EncryptionUtil.encrypt(password, optionalUser.get().getSalt()))) {
            throw new AuthenticationException("Wrong email or password. Email: " + email);
        }
        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!ValidationUtil.validateEmail(email)) {
            throw new RegistrationException("Invalid email " + email);
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " all ready exists");
        }
        return userService.add(new User(email, password));
    }
}
