package mate.academy.authentication.impl;

import java.util.Optional;
import mate.academy.authentication.AuthenticationService;
import mate.academy.exception.AuthenticationException;
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
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register. User with email "
                    + email + " already exists");
        } else if (password.isEmpty()) {
            throw new RegistrationException("Can't register. Invalid password. "
                    + "Please, try another password");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(HashUtil
                .hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't login. Email or password is invalid");
        }
        return userOptional.get();
    }
}
