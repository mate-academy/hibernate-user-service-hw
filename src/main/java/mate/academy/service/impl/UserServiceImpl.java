package mate.academy.service.impl;

import java.util.Optional;
import java.util.regex.Matcher;
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
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (isValidEmail(email)) {
            return userDao.findByEmail(email);
        }
        throw new DataProcessingException("Can't find user with email: " + email
                + " in DB or email is not valid", new Exception());
    }

    private boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        Pattern emailCompile = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailCompile.matcher(email);
        return emailMatcher.matches();
    }
}
