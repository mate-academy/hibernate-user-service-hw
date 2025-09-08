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
    public void register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("User with this email: "
                   + email + " already exist.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty()) {
            throw new AuthenticationException("User with email: "
                    + email + " does not exist");
        }

        User user = userFromDB.get();
        String hashedPasswordInput = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPasswordInput)) {
            return user;
        }
        throw new AuthenticationException("Wrong password, try again.");
    }
}
