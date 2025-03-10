package mate.academy.service.impl;

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
        if (user != null) {
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
            return userDao.add(user);
        }
        throw new DataProcessingException("Can't add new user. User cannot be null");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
