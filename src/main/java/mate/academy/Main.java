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
            authenticationService.register("test1@gmail.com", "1234");
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.register("test2@gmail.com", "1234");
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("test1@gmail.com", "1234");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("test2@gmail.com", "0");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("test3@gmail.com", "4321");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
