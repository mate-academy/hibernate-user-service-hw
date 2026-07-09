package mate.academy.service.impl;

import java.util.Optional;
import java.util.regex.Pattern;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);


    @Override
    public User add(User user) {

        if (user.getEmail() == null) {
            throw new RuntimeException("Email is null");
        }
        if (!emailPattern.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("Email is invalid");

        }
       if (user.getPassword() == null) {
           throw new RuntimeException("Password is null");
       }
       if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Password is empty");
       }

        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            throw new RuntimeException("Email is null");
        }
        if (!emailPattern.matcher(email).matches()) {
            throw new RuntimeException("Email is invalid");
        }
        return userDao.findByEmail(email);
    }
}
