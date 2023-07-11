package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.model.exception.AuthenticationException;
import mate.academy.model.exception.RegistrationException;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.orElseThrow(
            () -> new AuthenticationException("Wrong login or password"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't login user");
    }

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password can`t be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Account with email: " + email + " already exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }
}
