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
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password can`t be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Account with email: " + email + " already exist");
        }
        User user = new User(email, password);
        userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        return userService.findByEmail(email).filter(u -> u.getPassword()
                .equals(HashUtil.hashPassword(password, u.getSalt()))).orElseThrow(() ->
                new AuthenticationException("Can't login user"));
    }
}
