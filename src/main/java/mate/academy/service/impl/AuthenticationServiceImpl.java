package mate.academy.service.impl;

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
        if (password.isEmpty()) {
            throw new AuthenticationException("Empty password entered!");
        }
        if (email.isEmpty()) {
            throw new AuthenticationException("Empty email entered!");
        }
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Can`t find user with this email"));
        String acquiredHashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(acquiredHashedPassword)) {
            throw new AuthenticationException("Wrong password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Same email already registered");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Empty password entered!");
        }
        if (email.isEmpty()) {
            throw new RegistrationException("Empty email entered!");
        }
        User newUser = new User();
        newUser.setSalt(HashUtil.getSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));
        newUser.setEmail(email);
        return userService.add(newUser);
    }
}
