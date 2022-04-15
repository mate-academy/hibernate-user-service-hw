package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AlreadyExistingEmailException;
import mate.academy.exception.AuthenticationException;
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
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent()
                && userByEmailOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userByEmailOptional.get().getSalt()))) {
            return userByEmailOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws AlreadyExistingEmailException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new AlreadyExistingEmailException("User with such email: " + email
        + " has already been inserted to the DB");
    }
}
