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
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()
                && (HashUtil.hashPassword(password, userByEmail.get().getSalt()))
                .equals(userByEmail.get().getPassword())) {
            return userByEmail.get();
        }
        throw new AuthenticationException("The login or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            User registerUser = new User();
            registerUser.setEmail(email);
            registerUser.setPassword(password);
            userService.add(registerUser);
            return registerUser;
        }
        throw new RegistrationException("User with this email: "
                + email + " is already registered");
    }
}
