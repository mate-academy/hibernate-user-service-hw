package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("user@example.com", "password123");
            User loggedInUser = authenticationService.login("user@example.com", "password123");
            System.out.println("Successfully logged in: " + loggedInUser);
        } catch (RegistrationException | AuthenticationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
