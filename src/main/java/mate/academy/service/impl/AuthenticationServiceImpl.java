package mate.academy.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (userService.findByEmail(email).isPresent()) {
            User user = userService.findByEmail(email).get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException("Wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!isEmailValid(email)) {
            throw new RegistrationException("Email is not valid");
        }
        if (!isPasswordValid(password)) {
            throw new RegistrationException("Password is not valid");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty();
    }
}
