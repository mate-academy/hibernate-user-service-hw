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
        User user = userService.findByEmail(email).orElseThrow();
        if (!(user.getEMail().equals(email) && user.getPassword().equals(HashUtil
                .hashPassword(password, user.getSalt())))) {
            throw new AuthenticationException("Wrong login or password, can`t login");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEMail(email);
        user.setPassword(password);
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User already exist!");
        }
        return userService.add(user);
    }
}
