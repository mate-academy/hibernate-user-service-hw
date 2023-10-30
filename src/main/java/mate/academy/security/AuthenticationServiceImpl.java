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
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.save(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService
                .findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User with email "
                        + email + "doesn't exist"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Wrong password");
    }
}
