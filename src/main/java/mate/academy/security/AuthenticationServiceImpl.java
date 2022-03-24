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
    public void register(String login, String password) throws RegistrationException {
        Optional<User> findUser = userService.findByLogin(login);
        if (!findUser.isPresent()) {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            userService.add(user);
            return;
        }
        throw new RegistrationException("Can't register user to DB with login: " + login);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> findUser = userService.findByLogin(login);
        if (findUser.isPresent()
                && findUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, findUser.get().getSalt()))) {
            return findUser.get();
        }
        throw new AuthenticationException("Can't authenticate user with login: " + login);
    }
}
