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
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userPassword = user.getPassword();
            byte[] salt = user.getSalt();
            boolean flag =
                    user.getPassword().equals(HashUtil.hashPassword(userPassword, salt));
            if (flag) {
                return user;
            }
        }
        throw new AuthenticationException("Error authorization", new Exception());
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        try {
            user = userService.add(user);
            return user;
        } catch (NullPointerException e) {
            throw new RegistrationException("Can't register user:" + user, e);
        }
    }
}
