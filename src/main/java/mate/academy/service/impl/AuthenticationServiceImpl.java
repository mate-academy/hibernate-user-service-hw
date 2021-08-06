package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HashUtilSha512;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private static UserDao userDao;

    @Override
    public User register(String email, String password) {
        return userDao.add(new User(email, password, HashUtilSha512.getSalt()));
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userDao.findByEmail(login);
        if (user.isEmpty()) {
            throw new AuthenticationException("Login or password is wrong");
        }
        String hashPassword = HashUtilSha512.hashPassword(user.get().getPassword(),
                user.get().getSalt());
        if (hashPassword.equals(password)) {
            return user.get();
        }
        throw new AuthenticationException("Login or password is wrong ");
    }
}
