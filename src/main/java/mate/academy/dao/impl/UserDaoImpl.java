package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.model.User;

public class UserDaoImpl implements UserDao {
    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public Optional<User> get(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
