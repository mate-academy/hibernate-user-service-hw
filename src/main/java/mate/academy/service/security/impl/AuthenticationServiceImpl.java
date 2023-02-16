package mate.academy.service.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.service.security.AuthenticationService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            throw new RegistrationException("Sorry user with this "
                    + "email already register, try another email");
        }
        User user = new User(email, password);
        userService.add(user);
    }

    @Override
    public User authenticate(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty()) {
            throw new AuthenticationException("Can`t find user with email " + email);
        }
        User user = userFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Incorrect data, please try again");
        }
    }
}
