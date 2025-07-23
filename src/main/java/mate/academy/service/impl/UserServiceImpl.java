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
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User registerUser(String email, String password) {
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(password, salt);

        User user = new User();
        user.setEmail(email);
        user.setSalt(salt);
        user.setPassword(hashPassword);
        return add(user);
    }
}
