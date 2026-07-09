package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        Injector injector = Injector.getInstance("mate.academy");
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        authenticationService.register("artemnurmetow@gmail.com", "qwerty");
        authenticationService.login("artemnurmetow@gmail.com", "qwerty");
    }
}
