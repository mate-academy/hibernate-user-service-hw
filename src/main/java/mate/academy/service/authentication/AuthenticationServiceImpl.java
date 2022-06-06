package mate.academy.service.authentication;

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
        Optional<User> optionalUserFromDb = userService.findByEmail(email);
        if (optionalUserFromDb.isEmpty()
                || optionalUserFromDb.isPresent()
                && !optionalUserFromDb.get().getPassword().equals(HashUtil.hashPassword(password,
                optionalUserFromDb.get().getSalt()))) {
            throw new AuthenticationException("Can't authentication user with email: " + email);
        }
        return optionalUserFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUserFromDb = userService.findByEmail(email);
        if (optionalUserFromDb.isPresent()) {
            throw new RegistrationException("Can't register user with email: " + email);
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
