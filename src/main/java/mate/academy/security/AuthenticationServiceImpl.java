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
        Optional<User> optionalUser = userService.findByEmail(email);
        if (!email.isEmpty() || !password.isEmpty() || optionalUser.isPresent()) {
            User user = optionalUser.get();
            String currentlyPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(currentlyPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password is empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Wrong email or password");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
