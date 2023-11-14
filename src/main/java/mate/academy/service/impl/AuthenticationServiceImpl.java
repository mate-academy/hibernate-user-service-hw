package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Dao
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User foundedUser = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(String.format(
                        "User with login: %s is not exist", email)));

        String inputPassword = HashUtil.hashPassword(password, foundedUser.getSalt());

        if (foundedUser.getPassword().equals(inputPassword)) {
            return foundedUser;
        }

        throw new AuthenticationException(String.format("Incorrect password: %s", inputPassword));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format(
                    "User with email: %s is already exists", email));
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getSalt());

        String hashedPassword = HashUtil.hashPassword(password, newUser.getSalt());
        newUser.setPassword(hashedPassword);

        return userService.add(newUser);
    }
}
