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
        if (user != null) {
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
            return userDao.add(user);
        }
        throw new RuntimeException("Can't add user to the DB!");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return userDao.findByEmail(email);
        }
        return Optional.empty();
    }
}
