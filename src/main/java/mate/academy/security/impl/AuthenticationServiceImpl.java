package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public void register(String mail, String password) throws RegistrationException {
        if (userService.findByLogin(mail).isPresent()) {
            throw new RegistrationException("User with mail " + mail + "already exists");
        }
        var user = new User();
        user.setLogin(mail);
        user.setPassword(password);
        userService.save(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        var optionalUserFromDB = userService.findByLogin(login);
        if (optionalUserFromDB.isEmpty() || !HashUtil.hashPassword(password,
                        optionalUserFromDB.get().getSalt())
                .equals(optionalUserFromDB.get().getPassword())) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return optionalUserFromDB.get();
    }
}
