package mate.academy.dao.impl;

import mate.academy.dao.UserDao;
import mate.academy.model.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
