package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        User user = optionalUser.orElseThrow(AuthenticationException::new);
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException();
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try {
            User user = new User();
            user.setEmail(email);
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException(e);
        }
    }
}
