package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Login or password is not correct"));

        if (!user.getPassword().equals(password)) { // або хеш-пароль
            throw new AuthenticationException("Login or password is not correct");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Can`t add User with - this email already Exist");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);

        User registeredUser = userService.add(newUser);
        return registeredUser;
    }
}
