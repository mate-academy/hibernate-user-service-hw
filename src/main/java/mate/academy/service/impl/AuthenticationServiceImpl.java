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
    public User login(String loginEmail, String loginPassword) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(loginEmail);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException("such user does not exist " + loginEmail);
        }
        User user = byEmail.get();
        String hashedLoginPassword = HashUtil.hashPassword(loginPassword, user.getSalt());
        if (user.getPassword().equals(hashedLoginPassword)) {
            return user;
        }
        throw new AuthenticationException("Can`t authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("user with such email already exists " + email);
        }
        return new User(email, password);
    }
}
