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
        if (userService.emailExists(email)) {
            Optional<User> byEmail = userService.findByEmail(email);
            if (byEmail.isPresent()) {
                User user = byEmail.get();
                if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                    return user;
                } else {
                    throw new AuthenticationException("Password is incorrect! Try again ...");
                }
            }
            throw new AuthenticationException("Incorrect login, try again");
        }
        throw new AuthenticationException("Incorrect login, try again , login : " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.emailExists(email)) {
            throw new RegistrationException("User with this email already exists in db , email : "
                    + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.generateSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
