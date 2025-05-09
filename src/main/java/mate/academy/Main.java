package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);

        // Test Registration
        try {
            User newUser = authenticationService.register("test@example.com",
                    "securePassword123");
            System.out.println("Registered user: " + newUser);
        } catch (RegistrationException e) {
            System.err.println("Registration failed: " + e.getMessage());
        }

        // Test Login (successful)
        try {
            User loggedInUser = authenticationService.login("test@example.com",
                    "securePassword123");
            System.out.println("Logged in user: " + loggedInUser);
        } catch (AuthenticationException e) {
            System.err.println("Login failed (successful case expected): " + e.getMessage());
        }

        // Test Login (incorrect password)
        try {
            authenticationService.login("test@example.com", "wrongPassword");
            System.err.println("Login should have failed due to incorrect password!");
        } catch (AuthenticationException e) {
            System.out.println("Login failed (incorrect password): " + e.getMessage());
        }

        // Test Registration (duplicate email)
        try {
            authenticationService.register("test@example.com", "anotherPassword");
            System.err.println("Registration should have failed due to duplicate email!");
        } catch (RegistrationException e) {
            System.out.println("Registration failed (duplicate email): " + e.getMessage());
        }
    }
}
