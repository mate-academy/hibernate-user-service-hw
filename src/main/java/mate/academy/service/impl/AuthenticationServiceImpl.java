package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final UserService USER_SERVICE = (UserService) INJECTOR
            .getInstance(UserService.class);

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = USER_SERVICE.findByEmail(email);
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = USER_SERVICE.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("User is already registered");
        }
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Field user or password not be empty");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        try {
            USER_SERVICE.add(user);
        } catch (RuntimeException e) {
            throw new RegistrationException("Can't registered user:" + user);
        }
        return null;
    }

    private boolean matchPasswords(String password, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
