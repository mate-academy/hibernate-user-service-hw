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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromOptional = userService.findByEmail(email);
        if (userFromOptional.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }
        User user = userFromOptional.get();
        String hashPassword = HashUtil.hashPassword(password,user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {

        User user = new User();
        Optional<User> newUser = userService.findByEmail(email);
        if (newUser.isPresent()) {
            throw new RegistrationException("User already exists bt email:" + email);
        }
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
