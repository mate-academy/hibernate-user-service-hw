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
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        byte[] salt = HashUtil.getSalt();
        String password = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(password);
        user.setSalt(salt);
        userDao.add(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        return user;
    }
}
