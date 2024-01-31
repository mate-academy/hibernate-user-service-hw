package mate.academy.secure;

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
    public void register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
        } else {
            throw new RegistrationException("a user with a similar email already exist:" + email);
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userFromDbOptional.isEmpty() && !user.getPassword().equals(hashPassword)) {
            throw new AuthenticationException("can't authenticate user with login:" + email);
        } else {
            return user;
        }
    }
}


