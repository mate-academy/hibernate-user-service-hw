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
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found"));

        String hashed = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashed)) {
            throw new AuthenticationException("Incorrect password");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException(user + " already exists");
        }

        User userObj = new User();
        byte[] salt = HashUtil.getSalt();
        userObj.setSalt(salt);
        userObj.setEmail(email);
        userObj.setPassword(HashUtil.hashPassword(password, userObj.getSalt()));

        try {
            userService.save(userObj);
        } catch (Exception e) {
            throw new RegistrationException("Cannot save user: " + userObj);
        }
        return userObj;
    }
}
