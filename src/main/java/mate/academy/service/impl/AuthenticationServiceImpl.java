package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
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
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> findUserByEmailFromDB = userService.findByEmail(email);
        String hashPassword =
                HashUtil.hashPassword(password, findUserByEmailFromDB.get().getSalt());
        if (findUserByEmailFromDB.isEmpty() || !findUserByEmailFromDB.get()
                .getPassword().equals(hashPassword)) {
            throw new AuthenticationException("Can not find user with such email or password");
        }
        return findUserByEmailFromDB.get();
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can not register user with such " + email + "email");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
