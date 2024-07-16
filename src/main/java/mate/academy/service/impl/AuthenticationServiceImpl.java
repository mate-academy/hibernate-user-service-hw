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
        Optional<User> userFromFbOptional = userService.findByEmail(email);
        if (userFromFbOptional.isEmpty()
                || !userFromFbOptional.get().getPassword()
                        .equals(HashUtil.hashPassword(
                                password,
                                userFromFbOptional.get().getSalt())
                        )
        ) {
            throw new AuthenticationException("Wrong login or password", new Exception());
        }
        return userFromFbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromFbOptional = userService.findByEmail(email);
        if (userFromFbOptional.isPresent()) {
            throw new RegistrationException("Account by this email already exits: " + email,
                    new Exception());
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }
}
