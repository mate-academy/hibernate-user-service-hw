package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Cant authenticate user");
    }

    @Override
    public User register(String email, String password, String login)
            throws AuthenticationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setLogin(login);
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
            return userService.add(user);
        }
        throw new AuthenticationException("User with email " + email + " already exists");
    }
}
