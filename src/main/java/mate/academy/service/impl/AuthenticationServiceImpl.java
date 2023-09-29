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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can't find user with login: " + email);
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(
                password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user with login: " + email);
    }
    
    @Override
    public void register(String email, String password) throws RegisterException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegisterException("User with email is already register. Email: " + email);
        }
        User user = new User(email, password);
        userService.add(user);
    }
}
