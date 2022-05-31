package mate.academy.security;

import java.util.Objects;
import java.util.Optional;
import javax.naming.AuthenticationException;
import mate.academy.dao.UserService;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && Objects.equals(userOptional.get().getPassword(),
                HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            return userOptional.get();
        }
        throw new AuthenticationException("Cannot authenticate a user with the email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email is already exists " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
