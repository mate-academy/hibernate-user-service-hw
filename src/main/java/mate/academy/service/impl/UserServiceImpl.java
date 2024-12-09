package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.User;
import mate.academy.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User add(User user) {
        try {
            return userDao.add(user);
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't add user: " + user, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return userDao.findByEmail(email);
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't find user by email: " + email, e);
        }
    }
}
