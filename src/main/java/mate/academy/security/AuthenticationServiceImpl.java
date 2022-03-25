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
        Optional<User> findUser = userService.findByLogin(login);
        if (findUser.isPresent()) {
            throw new RegistrationException("Can't register user to DB with login: " + login);
        }
        User user = new User(login, password);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> findUser = userService.findByLogin(login);
        if (findUser.isPresent()) {
            User userFromDB = findUser.get();
            String hashPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
            if (userFromDB.getPassword().equals(hashPassword)) {
                return userFromDB;
            }
        }
        throw new AuthenticationException("Can't authenticate user with login: " + login);
    }
}
