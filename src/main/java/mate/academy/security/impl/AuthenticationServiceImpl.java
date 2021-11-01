package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByLogin(email);
        if (userOptional.isEmpty() || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByLogin(email).isPresent()) {
            throw new RegistrationException("User with mail " + email + " already exists");
        }
        return userService.add(new User(email, password));
    }
}
