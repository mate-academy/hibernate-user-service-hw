package mate.academy.dao;

import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.User;

public interface UserDao {
    User create(User user) throws DataProcessingException;

    Optional<User> findByEmail(String email) throws DataProcessingException;
}
