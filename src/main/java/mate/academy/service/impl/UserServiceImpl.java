package mate.academy.service.impl;

import mate.academy.model.User;
import mate.academy.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
