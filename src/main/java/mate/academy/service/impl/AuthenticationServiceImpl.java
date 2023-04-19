package mate.academy.service.impl;

import java.util.Random;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.SaltUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) {
        RuntimeException exception = new RuntimeException();
        User fondUser = userDao.findByEmail(email).orElseThrow(() -> exception);
        if (fondUser.getPassword().equals(SaltUtil.getSalt(password, fondUser.getSalt()))) {
            return fondUser;
        }
        throw exception;
    }

    @Override
    public User register(String email, String password) {
        RuntimeException exception = new RuntimeException();
        if (userDao.findByEmail(email).isPresent()) {
            throw exception;
        }
        Random random = new Random();
        byte[] salt = new byte[2];
        salt[0] = 12;//(byte) random.nextInt(0,12);
        salt[1] = 12;//(byte) random.nextInt(0,12);
        User toAdd = new User(
                email,
                SaltUtil.getSalt(password,salt),
                salt
        );
        userDao.add(toAdd);
        return toAdd;
    }
}
