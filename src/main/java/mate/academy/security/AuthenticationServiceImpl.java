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
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException("Password can`t be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("this email has been registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.save(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (!userByEmail.isEmpty()) {
            User user = userByEmail.get();
            String userPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(userPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Login or password are incorrect");
    }
}
