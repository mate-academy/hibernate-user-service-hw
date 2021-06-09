package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        System.out.println(authenticationService.register("bob@mate.com", "12423"));

        System.out.println(authenticationService.register("alice@test.com", "23124"));
        try {
            System.out.println(authenticationService.register("alice@test.com", "4"));
        } catch (RegistrationException e) {
            System.out.println("User exist");
        }

        try {
            System.out.println(authenticationService.login("alice@test.com",
                    "23124"));
        } catch (AuthenticationException e) {
            System.out.println("Exception");
        }

        try {
            System.out.println(authenticationService.login("bob@mate.com", "4"));
        } catch (AuthenticationException e) {
            System.out.println("Invalid password");
        }
    }
}
