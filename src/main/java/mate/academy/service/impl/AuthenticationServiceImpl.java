package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
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
    private UserDao userDao;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbByEmail = userDao.findByEmail(email);
        if (userFromDbByEmail.isEmpty()) {
            throw new AuthenticationException("This user not registered by this email: " + email);
        }
        User user = userFromDbByEmail.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Password incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbByEmail = userService.findByEmail(email);
        if (!userFromDbByEmail.isEmpty()) {
            throw new RegistrationException("This user is registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        return userDao.add(user);
    }
}
