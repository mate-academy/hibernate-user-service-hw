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
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException("Email is Empty");
        }
        User user = byEmail.get();
        String hashPassword = HashUtil
                .hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Wrong Password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email Already Exists");
        }
        userService.add(user);
        return user;
    }
}
