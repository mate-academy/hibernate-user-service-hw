package mate.academy.service.impl;

import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (userDao.findByEmail(email).isEmpty()) {
            throw new AuthenticationException("There isn't a user with email - " + email);
        }
        return userDao.findByEmail(email).get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userDao.findByEmail(email).isPresent()) {
            throw new RegistrationException("There is a user with email - " + email);
        }
        return userDao.add(new User(email, password));
    }
}
