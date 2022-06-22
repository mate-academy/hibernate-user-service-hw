package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("daniel@gmail.com", "daniel");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(authenticationService.login("daniel@gmail.com", "daniel"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
