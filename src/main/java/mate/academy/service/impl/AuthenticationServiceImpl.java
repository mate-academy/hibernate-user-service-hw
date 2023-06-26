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
        String hashedPassword = HashUtil.hashPassword(password, userByEmail.get().getSalt());
        if (userByEmail.isPresent()
                && hashedPassword.equals(userByEmail.get().getPassword())) {
            return userByEmail.get();
        } else {
            throw new AuthenticationException("Login is invalid");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userCheck = userService.findByEmail(email);
        if (userCheck.isPresent()) {
            throw new RegistrationException("Use a different email. User with "
                    + email + " is already registered");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
