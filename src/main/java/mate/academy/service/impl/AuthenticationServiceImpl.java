package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> UserFromDB = userService.findByEmail(email);
        if (UserFromDB.isEmpty()){
            throw new AuthenticationException("Can t authenticate User");
        }
        User user = UserFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }
        throw new AuthenticationException("Can t authenticate User");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return null;
    }
}
