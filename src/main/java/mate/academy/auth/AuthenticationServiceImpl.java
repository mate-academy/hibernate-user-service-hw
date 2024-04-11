package mate.academy.auth;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) {
        Optional<User> userInDb = userService.findByEmail(email);
        if (userInDb.isEmpty() || !userInDb.get().getPassword().equals(password)) {
            throw new AuthenticationException(
                            "There is no user in DB with such email");
        }
        return userInDb.get();
    }

    @Override
    public User register(String email, String password) {
        return null;
    }
}
