package mate.academy.dao;

import mate.academy.model.User;

public interface UserDao {
    User add(User user);

    User findByEmail(String email);
}
