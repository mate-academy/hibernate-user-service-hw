package mate.academy.dao.impl;

import java.util.List;
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
    public Optional<User> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }
}
