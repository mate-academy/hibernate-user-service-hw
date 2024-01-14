package mate.academy.security;

import java.util.Optional;
import javax.security.sasl.AuthenticationException;
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

        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }

        throw new AuthenticationException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("User with this email exist");
        }

        User user = new User();
        user.setEmail(email);

        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);

        user.setSalt(salt);
        user.setPassword(hashedPassword);

        User registeredUser = userService.add(user);

        return registeredUser;
    }
}
