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
        Optional<User> optionalUser = userService.findByEmail(email);
        User user = optionalUser.orElseThrow(AuthenticationException::new);
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException();
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email"
                    + " already exists");
        }
        if (!email.matches("[\\w._]+@[.\\w]+")) {
            throw new RegistrationException("Invalid email " + email
                    + ". Email should be in form: address@domen");
        }
        if (password.length() < 6) {
            throw new RegistrationException("Invalid password " + email
                    + ". Password should at least 6 symbols long");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
