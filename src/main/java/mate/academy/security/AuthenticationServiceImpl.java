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
    private User user;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDataBaseOptional = userService.findByEmail(email);
        if (userFromDataBaseOptional.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDataBaseOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return userService.add(user);
    }
}
