package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        String hash = HashUtil.hashPassword(password, optionalUser.get().getSelt());
        if (!optionalUser.isEmpty() && optionalUser.get().getSelt().equals(hash)) {
            return optionalUser.get();
        } else {
            throw new AuthenticationException("Please check your details!"
                    + "Your login or password is incorrect");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        } else {
            throw new RegistrationException("Please check your login or password!\n"
                    + "Maybe you already registration on this site?");
        }
    }
}
