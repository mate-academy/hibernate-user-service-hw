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
    private static final String CANT_LOGIN_EXCEPTION_MESSAGE =
            "Can't login. Login or password is wrong.";
    private static final String EXISTED_EMAIL_EXCEPTION_MESSAGE =
            "Can't register user. This email is already used";
    private static final String EMPTY_EMAIL_EXCEPTION_MESSAGE =
            "Email can't be empty";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()
                || isPasswordCorrect(userFromDbOptional.get().getPassword(),
                        HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException(CANT_LOGIN_EXCEPTION_MESSAGE);
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException(EMPTY_EMAIL_EXCEPTION_MESSAGE);
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(EXISTED_EMAIL_EXCEPTION_MESSAGE);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    private boolean isPasswordCorrect(String password,String hashedPassword) {
        return hashedPassword.equals(password);
    }
}
