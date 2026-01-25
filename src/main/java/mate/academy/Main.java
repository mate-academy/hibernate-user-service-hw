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

        String email = "test@mail.com";
        String password = "test";

        try {
            User user = authenticationService.register(email, password);
            System.out.println("User registered: " + user.getEmail());
        } catch (RegistrationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        try {
            User user = authenticationService.login(email, password);
            System.out.println("Login successful: " + user.getEmail());
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}
