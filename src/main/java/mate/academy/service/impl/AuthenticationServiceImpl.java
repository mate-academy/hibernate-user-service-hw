package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        if (optionalUserByEmail.isEmpty()
                || optionalUserByEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password,
                        optionalUserByEmail.get().getSalt()))) {
            throw new AuthenticationException("Email or password is incorrect.");
        }
        return optionalUserByEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        if (optionalUserByEmail.isPresent()) {
            throw new RegistrationException("User with current email (" + email
                    + ") already exists.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
