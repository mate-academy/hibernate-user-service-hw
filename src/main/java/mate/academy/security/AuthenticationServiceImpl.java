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
        if (email == null || password == null
                || email.isBlank() || password.isBlank()
                || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Incorrect email or password");
        }
        return userService.add(new User(email, password));
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()
                || !user.get().getPassword().equals(
                        HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user. "
                    + "Email or password is incorrect");
        }
        return user.get();
    }
}
