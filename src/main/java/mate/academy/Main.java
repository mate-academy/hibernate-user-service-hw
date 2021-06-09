package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("uncle_bob@gmail.com", "qwerty");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
        }
        try {
            authenticationService.login("uncle_bob@gmail.com", "qwerty");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
        }
        try {
            authenticationService.register("uncle_bob@gmail.com", "qwerty");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
        }

        try {
            authenticationService.login("unknown_email@gmail.com", "1234");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
        }
    }
}
