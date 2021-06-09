package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isPresent()) {
            throw new DataProcessingException("Current email " + email + " already exists in DB");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(email, userFromDb.get().getSalt()))) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }
}
