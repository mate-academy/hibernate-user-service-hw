package mate.academy.dao;

import mate.academy.model.User;

import java.util.Optional;

public interface UserDAO {
    User save(User user);

    Optional<User> findByMail(String mail);
}
