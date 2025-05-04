package mate.academy.service.impl;

import com.mysql.cj.util.StringUtils;
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
        validateData(email, password);
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("User with email " + email + " not found");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Wrong password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateData(email, password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    public void validateData(String email, String password) throws AuthenticationException {
        if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(password)) {
            throw new AuthenticationException("Email or password cannot be null");
        }
    }
}
