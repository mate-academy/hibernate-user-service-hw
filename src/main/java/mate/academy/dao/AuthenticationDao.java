package mate.academy.dao;

import mate.academy.model.User;

public interface AuthenticationDao {
    User login(String email, String password);
}
