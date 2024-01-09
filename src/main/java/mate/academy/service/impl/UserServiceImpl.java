package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public User get(Long id) throws EntityNotFoundException {
        return userDao.get(id).orElseThrow(() ->
                new EntityNotFoundException("Cannot find user by id: " + id));
    }

    @Override
    public Optional<User> get(String email) {
        return userDao.get(email);
    }
}
