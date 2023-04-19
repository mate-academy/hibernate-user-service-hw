package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}
