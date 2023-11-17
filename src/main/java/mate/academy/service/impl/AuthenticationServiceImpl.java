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
        Optional<User> userCheck = userService.findByEmail(email);
        User user = userCheck.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userCheck.isEmpty() || user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Wrong login or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Can't register user with empty data!");
        }
        Optional<User> userCheck = userService.findByEmail(email);
        if (userCheck.isPresent()) {
            throw new RegistrationException("This user already exists!");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
