package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws ReflectiveOperationException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new ReflectiveOperationException("User already present in the DB");
    }
}
