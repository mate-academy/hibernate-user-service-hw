package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HibernateUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Service
    private UserService userService;

    @Override
    public User login(String email, String password) {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new RegistrationException("User is not registered in the Db");
        }
        User user = userFromDb.get();
        String hashedPassword = HibernateUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new RegistrationException("Password are not correct");
        }
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setSalt(HibernateUtil.getSalt());
        String hashedPassword = HibernateUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        userService.add(user);
        return user;
    }
}
