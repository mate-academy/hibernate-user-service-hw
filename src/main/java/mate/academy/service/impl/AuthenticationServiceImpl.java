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
        if (userByEmail.isPresent() && isMatchPassword(password, userByEmail.get())) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Login or password was incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        return userService.add(new User(email, password));
    }

    private boolean isMatchPassword(String password, User userByEmail) {
        String hashPassword = HashUtil.hashPassword(password, userByEmail.getSalt());
        return userByEmail.getPassword().equals(hashPassword);
    }
}
