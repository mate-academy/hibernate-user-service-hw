package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()
                || userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException(String
                    .format("User by email %s doesn't exist or password is incorrect", email));
        }
        return userFromDb.get();
    }

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String
                    .format("User by email %s already exists", email));
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        user.setPassword(hashedPassword);
        userService.add(user);
    }
}
