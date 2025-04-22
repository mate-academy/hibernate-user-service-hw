package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService {
    User add(User user); 

    Optional<User> findByEmail(String email); 

    User update(User user); 

    boolean delete(String userId); 
}
