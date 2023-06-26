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
        Optional<User> userFromDb = userService.findByLogin(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("This email has not been registered yet");
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Couldn't login. Email or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByLogin(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("This email has already been registered");
        }
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password shouldn't be empty");
        }
        byte[] salt = HashUtil.getSalt();
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(HashUtil.hashPassword(password, salt));
        newUser.setSalt(salt);
        return userService.add(newUser);
    }
}
