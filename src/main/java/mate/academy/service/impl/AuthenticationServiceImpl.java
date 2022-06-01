package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
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
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByLogin(email).isPresent()) {
            throw new RegistrationException("User with current email " + email + " already exists");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByLogin(login);
        if (userOptional.isEmpty() || !checkPassword(userOptional.get(), password)) {
            throw new AuthenticationException("Wrong login or password");
        }
        return userOptional.get();
    }

    private boolean checkPassword(User user, String password) {
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashPassword);
    }
}
