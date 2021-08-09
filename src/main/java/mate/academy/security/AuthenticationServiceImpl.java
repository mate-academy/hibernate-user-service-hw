package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(login);
        if (userFromDbOptional.isPresent() && isPasswordValid(password, userFromDbOptional.get())) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException(
                "Can't authenticate user: login or password is incorrect");
    }

    private boolean isPasswordValid(String inputPassword, User user) {
        return user.getPassword().equals(HashUtil.hashPassword(inputPassword, user.getSalt()));
    }

    @Override
    public User register(String name, String login, String password) {
        return userService.add(new User(name, login, password));
    }
}
