package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("Alice", "4321");

        try {
            User alice = authenticationService.login("Alice", "4321");
            System.out.println(alice);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication error");
        }
    }
}
