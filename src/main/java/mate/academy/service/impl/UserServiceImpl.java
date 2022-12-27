package mate.academy.service.impl;

import java.util.List;
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
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Override
    public User add(User user) {
        byte[] salt = HashUtil.generateRandomSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(),user.getSalt()));
        return userDao.add(user);
    }
}
