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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Can't find login user");
        }
        User user = userOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Password is not correct");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email,password);
        Optional<User> userOptional = userService.findByEmail(user.getLogin());
        if (userOptional.isPresent()) {
            throw new RegistrationException("User with login " + user.getLogin()
                    + " already exist");
        }
        return userService.add(user);
    }
}
