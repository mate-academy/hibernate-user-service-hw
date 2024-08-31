package mate.academy;

import mate.academy.dao.UserDao;
import mate.academy.dao.impl.UserDaoImpl;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.service.impl.AuthenticationServiceImpl;
import mate.academy.service.impl.UserServiceImpl;

public class Main {
    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService);
        authenticationService.register("mykhailo123", "qwerty");
        authenticationService.login("mykhailo123", "qwerty");
    }
}
