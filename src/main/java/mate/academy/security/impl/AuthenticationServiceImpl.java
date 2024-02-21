package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
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
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) {
        Optional<User> userByEmailFromDbOptional = userService.findByEmail(email);
        try {
            if (userByEmailFromDbOptional.isEmpty()) {
                throw new AuthenticationException("Incorrect email: " + email);
            }
            User userFromDbByEmail = userByEmailFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, userFromDbByEmail.getSalt());
            if (userFromDbByEmail.getPassword().equals(hashedPassword)) {
                return userFromDbByEmail;
            }
            throw new AuthenticationException("Wrong password");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't authenticate user by email '" + email
                    + "' and password.", e);
        }

    }

    @Override
    public User register(String email, String password) {
        User newUser = null;
        try {
            Optional<User> userFromDb = userDao.findByEmail(email);
            if (userFromDb.isPresent()) {
                throw new RegistrationException("User by email '" + email + "' already exists,"
                        + "please use another login or restore password");
            }
            newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            userService.add(newUser);
        } catch (RegistrationException registrationException) {
            throw new RuntimeException("You cannot register with the email you entered");
        }
        return newUser;
    }
}
