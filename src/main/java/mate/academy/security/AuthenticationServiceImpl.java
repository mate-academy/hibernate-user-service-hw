package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Password or login is incorrect"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!(hashedPassword.equals(user.getPassword()))) {
            throw new AuthenticationException("Password or login is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("A user with this email already exists");
        }
        User user = new User();
        user.setPassword(password);
        user.setLogin(email);
        return userService.add(user);
    }
}
