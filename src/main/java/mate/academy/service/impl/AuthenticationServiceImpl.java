package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegisterException;
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
        if (userFromDb.isEmpty() || !userFromDb.get()
                .getPassword().equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))
                || userFromDb.get().getEmail().isEmpty()) {
            throw new AuthenticationException("Can't login user. Invalid email or password");
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Wrong password. Enter again");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegisterException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegisterException("Can't register user. User already exists, please login");
        }
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegisterException("Password or email couldn't be empty");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
