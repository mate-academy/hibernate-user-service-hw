package mate.academy.service.impl;

import java.util.Arrays;
import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Authentication failed."
                        + " User not found."));

        byte[] storedSalt = HashUtil.generateSalt();
        byte[] hashedPassword = HashUtil.hashPassword(password, storedSalt);

        if (!Arrays.equals(hashedPassword, user.getPassword())) {
            throw new AuthenticationException("Authentication failed. Incorrect password.");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userDao.findByEmail(email).isPresent()) {
            throw new RegistrationException("Registration failed. "
                    + "User with this email already exists.");
        }

        byte[] salt = HashUtil.generateSalt();
        byte[] hashedPassword = HashUtil.hashPassword(password, salt);

        User newUser = new User(email, hashedPassword, salt);
        return userDao.add(newUser);
    }
}
