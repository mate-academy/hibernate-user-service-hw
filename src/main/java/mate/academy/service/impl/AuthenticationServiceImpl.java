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
            throw new RegistrationException("This user was already register " + email);
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByLogin(login);
        User user = userOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!userOptional.isEmpty() && hashPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }
}
