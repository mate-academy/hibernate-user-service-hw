package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("email121@gmail.com", "password");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(authenticationService.login("email@gmail.com", "password"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
