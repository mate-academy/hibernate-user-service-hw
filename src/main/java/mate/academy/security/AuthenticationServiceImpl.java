package mate.academy.security;

import java.util.NoSuchElementException;
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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty()
                || !HashUtil.hashPassword(password, userFromDB.get().getSalt())
                        .equals(userFromDB.get().getPassword())) {
            throw new AuthenticationException("Cant authenticate user");
        }
        return userFromDB.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        User user = userFromDB.orElseThrow(() ->
                new NoSuchElementException("Can't registered."));
        userService.add(user);
        return user;
    }

}
