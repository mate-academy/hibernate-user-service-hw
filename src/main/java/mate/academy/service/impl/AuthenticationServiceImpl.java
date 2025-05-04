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
        Optional<User> user = userService.findByEmail(email);
        String userHashPassword = HashUtil.hashPassword(password, user.get().getSalt());
        String userPasswordFromDB = user.get().getPassword();
        String userEmail = user.get().getEmail();
        if (email.equals(userEmail) && userPasswordFromDB.equals(userHashPassword)) {
            return user.get();
        } else {
            throw new AuthenticationException("Can't find corresponding User " + userEmail);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isEmpty()) {
            user.setEmail(email);
            user.setPassword(password);
            return user;
        } else {
            throw new RegistrationException("User with email " + email + " already exist");
        }
    }
}
