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
        Optional<User> findedUserFromDBOptional = userService.findByEmail(email);
        if (findedUserFromDBOptional.isEmpty()) {
            throw new AuthenticationException("User does not exist");
        }
        User user = findedUserFromDBOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Password is not correct.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return userService.add(new User(email, password));
    }
}
