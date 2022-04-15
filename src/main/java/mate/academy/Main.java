package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService = (AuthenticationService)
            injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("alice@email.com", "1234");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register Alice");
        }

        try {
            authenticationService.register("bob@email.com", "qwerty");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register Bob");
        }

        try {
            User alice = authenticationService.login("alice@email.com", "1234");
            System.out.println(alice);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login Alice", e);
        }

        try {
            User bob = authenticationService.login("bob@email.com", "qwerty");
            System.out.println(bob);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login Bob", e);
        }
    }
}
