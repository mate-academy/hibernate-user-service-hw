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
        if (user.isPresent() && user.get().getPassword()
                .equals(HashUtil.getHashPassword(password, user.get().getSalt()))) {
            return user.get();
        }
        throw new AuthenticationException("Wrong authentication data");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() && userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("Can`t register user with e-mail: " + email);
        }
        return userService.add(new User(email, password));
    }
}
