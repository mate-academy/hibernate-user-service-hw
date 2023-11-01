package mate.academy;

import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("user@example.com", "password123");
        User loggedInUser = authenticationService.login("user@example.com", "password123");
        if (loggedInUser == null) {
            throw new RegistrationException("User is not registered");
        }
        System.out.println("User successfully registered");
    }
}
