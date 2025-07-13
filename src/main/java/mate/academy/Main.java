package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        System.out.println("Registering user...");
        try {
            User registeredUser = authService.register("user@example.com", "securePassword");
            System.out.println("User registered: " + registeredUser.getEmail());
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
        System.out.println("Logging in...");
        try {
            User loggedInUser = authService.login("user@example.com", "securePassword");
            System.out.println("User logged in: " + loggedInUser.getEmail());
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}
