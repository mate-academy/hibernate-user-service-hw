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
    private UserService userService;
    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && isPasswordValid(userOptional.get(), password)) {
            return userOptional.get();
        }
        throw new AuthenticationException("Can`t authenticate user");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userOptionalRegister =userService.findByEmail(email);
            if (userOptionalRegister.isEmpty()) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                return userService.add(user);
            }
        throw new RegistrationException("Can`t register user");
    }

    private boolean isPasswordValid(User user, String password) {
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashPassword.equals(password);
    }
}
