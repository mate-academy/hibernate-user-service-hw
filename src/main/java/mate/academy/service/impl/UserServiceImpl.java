package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        // Generowanie soli
        String salt = HashUtil.getSalt();
        user.setSalt(salt);
        // Hashowanie hasła
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}

