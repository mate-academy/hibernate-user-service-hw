package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;

    private AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException();
        }
        User user = byEmail.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);

        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(password, salt));

        return userService.add(user);
    }

}
