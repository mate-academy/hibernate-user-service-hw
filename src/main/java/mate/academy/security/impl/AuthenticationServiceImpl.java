package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;
    
    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            String hashedPassword
                    = HashUtil.hashPassword(password, userFromDbOptional.get().getSalt());
            if (userFromDbOptional.get().getPassword().equals(hashedPassword)) {
                return userFromDbOptional.get();
            }
        }
        throw new AuthenticationException("Access is denied! Login or password are invalid");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        if (userService.findByEmail(email).isEmpty()) {
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Failed to register. Email address already exists");
    }
}
