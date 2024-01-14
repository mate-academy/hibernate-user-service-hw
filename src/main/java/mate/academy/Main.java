package mate.academy;

import javax.security.sasl.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.security.RegistrationException;

public class Main {
    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        Injector injector = Injector.getInstance("mate.academy");
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User registeredUser = authenticationService.register("barcelona@gmail.com", "piotrek");
        System.out.println("Registered user: " + registeredUser);

        User loggedInUser = authenticationService.login("barcelona@gmail.com", "piotrek");
        System.out.println("Logged in user: " + loggedInUser);

    }
}
