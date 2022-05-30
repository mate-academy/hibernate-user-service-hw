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
        String hashedPassword = HashUtil.hashPassword(password,
                userFromDbOptional.get().getSalt());
        if (userFromDbOptional.isEmpty()
                && !userFromDbOptional.get().getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Incorrect login or password...");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            throw new RegistrationException("User with current email already exists");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
