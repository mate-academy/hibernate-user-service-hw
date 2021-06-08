package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService = (AuthenticationService)
            injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("valyeriy@gmail.com", "12345");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.register("valyeriy@gmail.com", "12345");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("valyeriy@gmail.com", "122345");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("valyeriy@gmail.com", "12345");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
