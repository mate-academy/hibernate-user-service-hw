package mate.academy.service.impl;

import java.util.Optional;
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
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isEmpty() || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't find user with such login and password");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (userDao.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email " + email
                    + ": email was already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userDao.add(user);
    }
}
