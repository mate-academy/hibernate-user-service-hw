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
    public User login(String email, String password) {
        Optional<User> findUser = userService.findByLogin(email);
        if (findUser.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        String hashPassword = HashUtil.hashPassword(password, findUser.get().getSalt());
        if (findUser.get().getPassword().equals(hashPassword) && !password.isEmpty()) {
            return findUser.get();
        }
        throw new AuthenticationException("The email or password that you entered is incorrect");
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByLogin(email).isEmpty()) {
            User newUser = new User();
            newUser.setLogin(email);
            newUser.setPassword(password);
            return userService.save(newUser);
        }
        throw new RegistrationException("User with email: " + email + " - already exist");
    }
}
