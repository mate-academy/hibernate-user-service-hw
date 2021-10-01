package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByLogin(login).isPresent()) {
            throw new RegistrationException("Can't register, user with email"
                     + login);
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.save(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isEmpty() || !(userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt())))) {
            throw new AuthenticationException("Invalid login or password");
        }
        return userFromDbOptional.get();
    }
}
