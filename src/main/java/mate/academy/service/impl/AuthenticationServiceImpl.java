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
        Optional<User> userFromBdOptional = userService.findByEmail(email);
        if (userFromBdOptional.isEmpty()
                || !userFromBdOptional
                .get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, userFromBdOptional.get().getSalt()))) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        return userFromBdOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email is already exist");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
