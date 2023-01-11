package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userPassword = user.getPassword();
            byte[] salt = user.getSalt();
            boolean flag =
                    HashUtil.hashPassword(password, salt).equals(HashUtil.hashPassword(userPassword, salt));
            if (flag) {
                return user;
            }
        }
        throw new AuthenticationException("Error authorization", new Exception());
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return null;
    }
}
