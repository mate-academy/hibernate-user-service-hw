package mate.academy;

import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final UserDao userDao = (UserDao) injector.getInstance(UserDao.class);
    public static final UserService userService = (UserService) injector
            .getInstance(UserService.class);
    public static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("loo@gmail.com", "123");
            System.out.println(authenticationService.login("loo@gmail.com", "123"));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Error login", e);
        } catch (RegistrationException e) {
            throw new RuntimeException("this is email used, please use other email", e);
        }
    }
}
