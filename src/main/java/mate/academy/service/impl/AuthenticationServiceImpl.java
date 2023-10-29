package mate.academy.service.impl;

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
        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                new AuthenticationException("Can`t get user for DB" + email));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Wrong password: " + password);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User newUser = new User();
        newUser.setEmail(setNewUserEmail(email));
        byte[] salt = newUser.getSalt();
        newUser.setSalt(salt);
        newUser.setPassword(password);
        userService.add(newUser);
        return newUser;
    }

    private String setNewUserEmail(String email) throws RegistrationException {
        String mail = "@gmail.com";
        if (email.contains(mail)) {
            return email;
        } else {
            throw new RegistrationException("Email must match pattern: " + mail);
        }
    }

    private byte[] setPassword(String password,byte[] salt) throws RegistrationException {
        if (password.length() > 4) {
            return HashUtil.hashPassword(password,salt).getBytes();
        } else {
            throw new RegistrationException("Password length must more than 4: " + password);
        }
    }
}
