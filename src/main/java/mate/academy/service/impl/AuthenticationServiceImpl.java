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
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            throw new AuthenticationException("Can't authentication of user");
        } else {
            String hashedPassword = HashUtil.hashPassword(password, user.get().getSalt());
            if (hashedPassword.equals(user.get().getPassword())) {
                return user.get();
            } else {
                throw new AuthenticationException("Can't authentication of user");
            }
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() | password.isEmpty()) {
            throw new RegistrationException("Fields e-mail and password must be fill out");
        } else if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Such e-mail is used! " + email);
        } else {
            byte [] salt = HashUtil.getSalt();
            User user = new User();
            user.setEmail(email);
            user.setSalt(salt);
            user.setPassword(HashUtil.hashPassword(password, salt));
            userService.add(user);
            return user;
        }
    }
}
