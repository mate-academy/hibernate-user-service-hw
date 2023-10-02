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
        if (userFromDbOptional.isPresent() && checkPassword(password, userFromDbOptional)) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Email or password is invalid: " + email);
    }
    
    @Override
    public void register(String email, String password) throws RegisterException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegisterException("User with email is already register. Email: " + email);
        }
        User user = new User(email, password);
        userService.add(user);
    }
    
    private boolean checkPassword(String password, Optional<User> userFromDbOptional) {
        if (userFromDbOptional.isEmpty()) {
            return false;
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(
                password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
