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
        if (userOptional.isEmpty() || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Couldn't login user with email = " + email
                    + ". Password is incorrect or user with this email does not exist.");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.isEmpty()) {
            throw new RegistrationException("Couldn't register new user with email = " + email
                    + ". Password is empty or user with this email already exists.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
