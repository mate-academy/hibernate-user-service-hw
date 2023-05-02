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
        Optional<User> userFindByEmail = userService.findByEmail(email);
        if (userFindByEmail.isPresent()) {
            User user = userFindByEmail.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can`t authentication user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFindByEmail = userService.findByEmail(email);
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("can`t registration user, please try again ");
        }

        if (userFindByEmail.isPresent()) {
            throw new RegistrationException("User " + email
                    + " is exist, please login");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(password);
        return userService.add(user);
    }
}
