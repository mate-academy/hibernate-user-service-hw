package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.UserAuthenticationException;
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
    public User login(String email, String password) throws UserAuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, optionalUser.get().getSalt()))) {
            return optionalUser.get();
        }
        throw new UserAuthenticationException("email or password are incorrect");
    }

    @Override
    public User register(String email, String password) {
        if (email == null || password == null
                || password.length() == 0 || email.length() == 0) {
            throw new RuntimeException("Incorrect email or password");
        }
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RuntimeException("There is already an email in DB:");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
