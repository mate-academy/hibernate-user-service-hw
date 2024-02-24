package mate.academy.security;

import java.util.Optional;
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
    public void register(String email, String password) {
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) {
        Optional<User> userFromDbOptional = userService.findByEmail(login);
        if (userFromDbOptional.isEmpty()) {
            throw new mate.academy.exception.AuthenticationException("Can't authenticate user");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new mate.academy.exception.AuthenticationException("Can`t authenticate user");
    }
}
