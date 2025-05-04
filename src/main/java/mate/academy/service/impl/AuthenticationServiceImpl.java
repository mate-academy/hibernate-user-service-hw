package mate.academy.service.impl;

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
        User user = userService.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("Can't find user by email: " + email));
        checkPassword(password, user);
        return user;
    }
    
    @Override
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email is already register. Email: " + email);
        }
        User user = new User(email, password);
        userService.add(user);
    }
    
    private void checkPassword(String password, User user) throws AuthenticationException {
        String hashedPassword = HashUtil.hashPassword(
                password, user.getSalt());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Email or password is invalid.");
        }
    }
}
