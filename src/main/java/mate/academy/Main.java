package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        String email = "bohdan@gmail.com";
        String password = "1234";
        try {
            System.out.println("Registration user with email: " + email + " and password: " + password);
            authenticationService.register(email, password);
            System.out.println("Getting user from DB with login()");
            System.out.println(authenticationService.login(email, password));
        } catch (RegistrationException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
