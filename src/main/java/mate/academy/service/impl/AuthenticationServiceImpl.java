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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPass = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPass.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Wrong email or password : " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("This email address already exists : "
                    + email);
        }
        if (password.isEmpty()) {
            throw new RegistrationException("The password must not be empty");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
