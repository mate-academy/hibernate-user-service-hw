package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            User registration = authenticationService.register("test@email.com", "1111");
            System.out.println(registration);
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            User login = authenticationService.login("test@email.com", "1111");
            System.out.println(login);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
