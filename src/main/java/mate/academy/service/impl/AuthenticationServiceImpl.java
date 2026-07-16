package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        User user = userOptional.get();
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("User is already exist");
        }
        String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(email, newPassword);
        return userDao.add(user);
    }
}
