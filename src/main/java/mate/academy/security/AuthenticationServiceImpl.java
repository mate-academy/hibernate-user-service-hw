package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (email.isBlank() || password.isBlank()) {
            throw new RegistrationException("Email and password cannot be empty");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByEmail(login)
                .orElseThrow(() -> new AuthenticationException("Cannot authenticate user"));

        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashPassword)) {
            throw new AuthenticationException("Cannot authenticate user");
        }
        return user;
    }
}
