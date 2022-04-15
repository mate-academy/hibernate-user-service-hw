package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;
import mate.academy.security.AuthenticationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger =
            LogManager.getLogger(AuthenticationServiceImpl.class);
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("alice@gmail.com", "1584");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
        }
        try {
            authenticationService.register("bob@gmail.com", "qwerty");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
        }
        try {
            authenticationService.register("marta@gmail.com", "");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
        }

        try {
            authenticationService.login("bob@gmail.com", "qwerty");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
        }
        try {
            authenticationService.login("noname@gmail.com", "1234");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
        }
    }
}
