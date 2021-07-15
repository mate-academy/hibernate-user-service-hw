package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Dao;
import mate.academy.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
