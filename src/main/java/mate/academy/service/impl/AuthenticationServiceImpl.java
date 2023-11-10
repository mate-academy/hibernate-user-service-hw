package mate.academy.service.impl;

import mate.academy.dao.UserDAO;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HashUtil;
import java.util.Arrays;
import java.security.MessageDigest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserDAO userDAO;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Authentication failed. User not found."));

        byte[] storedSalt = HashUtil.generateSalt();
        byte[] hashedPassword = HashUtil.hashPassword(password, storedSalt);

        if (!Arrays.equals(hashedPassword, user.getPassword())) {
            throw new AuthenticationException("Authentication failed. Incorrect password.");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userDAO.findByEmail(email).isPresent()) {
            throw new RegistrationException("Registration failed. User with this email already exists.");
        }

        byte[] salt = HashUtil.generateSalt();
        byte[] hashedPassword = HashUtil.hashPassword(password, salt);

        User newUser = new User(email, hashedPassword, salt);
        return userDAO.add(newUser);
    }
}
