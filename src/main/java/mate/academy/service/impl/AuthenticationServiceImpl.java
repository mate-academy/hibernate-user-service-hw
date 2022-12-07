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
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()
                && user.get().getPassword().equals(HashUtil.hashPassword(password))) {
            return user.get();
        }
        throw new AuthenticationException(
                String.format("Email or password aren't valid "
                        + "or user with email=%s wasn't registered", email));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            User uniqueUser = new User();
            uniqueUser.setEmail(email);
            uniqueUser.setPassword(password);
            userService.add(uniqueUser);
            return uniqueUser;
        }
        throw new RegistrationException(
                String.format("User with email=%s already created", email));
    }
}
