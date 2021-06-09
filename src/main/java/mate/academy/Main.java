package mate.academy;

import mate.academy.exception.AlreadyExistingEmailException;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            System.out.println(authenticationService.register("alicecooper@gmail.com", "blablapass007"));
        } catch (AlreadyExistingEmailException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.register("bobmarley@res.com", "666"));
        } catch (AlreadyExistingEmailException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.login("alicecooper@gmail.com", "blablapass007"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.login("bbobmarley@res.com", "6666"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

    }
}