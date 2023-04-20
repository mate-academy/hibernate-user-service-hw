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
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User add(String email, String password) {
        byte[] salt = HashUtil.getSalt();
        User toAdd = new User(
                email,
                HashUtil.hashPassword(password,salt),
                salt
        );
        return userDao.add(toAdd);
    }
}
