package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.hashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;

    @Override
    public User registerUser(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setSalt(hashUtil.getSalt());
            newUser.setPassword(hashUtil.hashPassword(password, newUser.getSalt()));
            return newUser;
        }
        throw new RegistrationException("This email "
                + email + "has already registered!");
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(hashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException("The login or the password are wrong!");
    }
}
