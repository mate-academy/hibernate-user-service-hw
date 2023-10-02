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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty() || userOptional.get().getPassword().equals(
                HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Invalid email or password");
        } else {
            return userOptional.get();
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            byte[] salt = HashUtil.getSalt();
            User newUser = new User(email, HashUtil.hashPassword(password, salt));
            newUser.setSalt(salt);
            return userService.add(newUser);
        } else {
            throw new RegistrationException("A user with this email: "
                    + email + " already exists");
        }
    }
}
