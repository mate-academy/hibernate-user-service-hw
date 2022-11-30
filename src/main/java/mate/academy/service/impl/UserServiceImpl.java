package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDAO;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDAO userDAO;

    @Override
    public User add(User user) {
        return userDAO.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
