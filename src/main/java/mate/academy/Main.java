package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("bob@gmail.com", "bob1234");
            authenticationService.register("alis@gmail.com", "alis1234");
            authenticationService.register("alis@gmail.com", "1234");

        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("User "
                    + authenticationService.login("alis@gmail.com", "alis1234")
                    + " passed login");
            System.out.println("User "
                    + authenticationService.login("gosha@gmail.com", "")
                    + " passed login");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

    }
}
