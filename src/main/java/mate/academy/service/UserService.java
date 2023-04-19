package mate.academy.service;

import java.util.List;
import mate.academy.model.User;

public interface UserService {
    User findByEmail(String email);

    List<User> getAll();
}
