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
        Optional<User> foundUser = userService.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new AuthenticationException("Email :"
                    + email
                    + "is incorrect, try input the correct one");
        }
        if (password.isEmpty()) {
            throw new AuthenticationException("Password is empty, you need to input another one");
        }
        User user = foundUser.get();
        String hashedPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Password: "
                + password
                + " doesn't match, try another one");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("Email :" + email + "already exists, try another one");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password is empty, try to input the correct one");
        }
        User registerUser = new User(email, password);
        return userService.add(registerUser);
    }
}
