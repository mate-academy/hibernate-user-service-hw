package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    private static final int SALT_SIZE = 16;

    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.getSalt(SALT_SIZE));
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
