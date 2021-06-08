package mate.academy.security;

import java.util.Optional;
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
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()
                && HashUtil.hashPassword(password, userByEmail.get().getSalt())
                .equals(userByEmail.get().getPassword())) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Wrong password or login");
    }

    @Override
    public User register(String email, String password) {
        return userService.add(new User(email, password));
    }
}
