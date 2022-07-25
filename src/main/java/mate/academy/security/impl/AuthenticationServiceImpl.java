package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.EmailValidatorService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;
    @Inject
    private EmailValidatorService emailValidatorService;

    @Override
    public User register(String email, String password) throws RegistrationException {
        emailValidatorService.isValid(email);
        if (!userService.findByEmail(email).isPresent()) {
            User user = new User(email, password);
            return userService.add(user);
        }
        throw new RegistrationException("User with such email has already registered in DB");
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() & userFromDB.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDB.get().getSalt()))) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Cannot authenticate user by input");
    }
}
