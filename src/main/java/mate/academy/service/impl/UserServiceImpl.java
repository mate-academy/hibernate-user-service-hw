package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Dao;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Dao
    private UserDao dao;

    @Override
    public User add(User user) {
        return dao.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
