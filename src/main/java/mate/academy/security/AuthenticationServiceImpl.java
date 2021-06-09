package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword()
                .equals(HashUtil.hash(password, optionalUser.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user by email " + email);
        }
        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        User user = new User();
        if (userService.findByEmail(email).isEmpty()) {
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new AuthenticationException("Can't register a new user. Such email " + email
                    + " is already exists. Please enter another email!");
    }
}
