package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty() && password.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user with email = "
                    + email, new Exception());
        }
        User userFromDb = userFromDbOptional.orElseThrow(() ->
                new DataProcessingException("Can't find user in DB", new Exception()));

        String hashPassword = HashUtil.hashPassword(password, userFromDb.getSalt());

        if (userFromDb.getPassword().equals(hashPassword)) {
            return userFromDb;
        }
        throw new AuthenticationException("Can't authenticate user with email = "
                + email, new Exception());
    }

    @Override
    public User register(String email, String password) {
        User inputUser = new User();
        Optional<User> userFromDbOptional = userService.findByEmail(email);

        if (!password.isEmpty() && userFromDbOptional.isEmpty()) {
            inputUser.setPassword(password);
            inputUser.setEmail(email);
            return inputUser;
        }
        throw new RegistrationException("Can't registration user with email: " + email
                + ". User with email already exist or email does not valid", new Exception());
    }

}
