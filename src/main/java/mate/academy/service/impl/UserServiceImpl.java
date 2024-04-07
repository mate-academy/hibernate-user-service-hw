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
        String password = user.getPassword();
        byte[] salt = user.getSalt();

        String hashedPassword = HashUtil.hashPassword(password, salt);
        user.setPassword(hashedPassword);
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userDao.getByEmail(email).orElseThrow(() ->
                new RuntimeException("There's no user with email " + email)));
    }
}
