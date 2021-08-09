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
        Optional<User> currentUser = userService.findByEmail(email);
        if (currentUser.isEmpty() || !currentUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, currentUser.get().getSalt()))) {
            throw new AuthenticationException("User with email: " + email
                    + "is not found or password is invalid.");
        }
        return currentUser.get();
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with email: " + email
                    + " is already exists in DB");
        }
        return userService.add(new User(email, password));
    }
}
