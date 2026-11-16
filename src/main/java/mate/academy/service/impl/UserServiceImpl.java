package mate.academy.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        try {
            String salt = HashUtil.generateSalt();
            String hashedPassword = HashUtil.hashPassword(user.getPassword(), salt);

            user.setSalt(salt);
            user.setPassword(hashedPassword);

            return userDao.add(user);
        } catch (NoSuchAlgorithmException e) {
            throw new DataProcessingException("Can't hash user password", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
