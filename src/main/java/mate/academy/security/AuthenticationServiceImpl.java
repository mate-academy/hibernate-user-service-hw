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
        Optional<User> userByEmail = userService.findByEmail(email);
        User user = userByEmail.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Authentication are failed by this email: "
                    + email + " and password " + password);
        }
        return user;

    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("this email already exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
