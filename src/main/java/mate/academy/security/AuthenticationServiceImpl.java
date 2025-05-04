package mate.academy.security;

import java.util.Optional;
import javax.naming.AuthenticationException;
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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userFromDbOptional.isEmpty() || !user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can`t authenticate user");
        }
        return user;
    }

    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (!optionalUser.isEmpty()) {
            throw new RegistrationException("User is allready exist !");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
    }
}
