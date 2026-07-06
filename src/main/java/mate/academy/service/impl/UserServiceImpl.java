package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.HashUtil;
import mate.academy.security.SaltUtil;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    public UserServiceImpl() {
    }

    @Override
    public User add(User user) {
        byte[] salt = SaltUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(
                user.getPassword(),
                salt
        ));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
