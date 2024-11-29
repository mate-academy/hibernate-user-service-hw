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
            User registeredUser = authenticationService.register("test@example.com", "password123");
            System.out.println("Registered user: " + registeredUser);
        } catch (RegistrationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        try {
            User loggedInUser = authenticationService.login("test@example.com", "password123");
            System.out.println("Logged in user: " + loggedInUser);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }
}
