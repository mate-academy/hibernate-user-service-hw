package mate.academy.security;

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
    public void register(String email, String password) {
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.save(user);
        } catch (Exception e) {
            throw new RegistrationException("Can't register user for email " + email);
        }
    }

    @Override
    public User login(String email, String password) {
        try {
            User user = userService.findByEmail(email).orElseThrow(RuntimeException::new);
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
            throw new RuntimeException();
        } catch (Exception e) {
            throw new AuthenticationException("Email or password is invalid.");
        }
    }
}
