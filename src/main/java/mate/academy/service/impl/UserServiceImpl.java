package mate.academy.service.impl;

import jakarta.persistence.PersistenceException;
import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is null or empty");
        }
        String clearEmail = user.getEmail().trim().toLowerCase();
        user.setEmail(clearEmail);
        if (userDao.findByEmail(clearEmail).isPresent()) {
            throw new RuntimeException("Email " + user.getEmail() + " already exist");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password is null or empty");
        }
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(hashPassword);
        try {
            return userDao.add(user);
        } catch (PersistenceException e) {
            throw new RuntimeException("Email " + user.getEmail() + " already exist", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        String clearEmail = email.trim().toLowerCase();
        return userDao.findByEmail(clearEmail);
    }
}
