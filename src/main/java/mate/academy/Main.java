package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            System.out.println(authenticationService.register("bob@res.com", "123"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.register("alice@res.com", "234"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.register("alice@res.com", "4"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.login("alice@res.com", "234"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(authenticationService.login("bob@res.com", "4"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
