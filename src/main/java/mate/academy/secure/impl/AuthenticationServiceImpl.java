package mate.academy.secure.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.secure.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.service.impl.UserServiceImpl;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService = new UserServiceImpl();

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(
                        "User does not exist with email: " + email));

        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashPassword)) {
            throw new AuthenticationException(
                    "Cant authenticate because of wrong password: " + password);
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Can`t register because this email already exists: " + email);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
