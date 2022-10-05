package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        String email = "anna@gmail.com";
        String password = "1234";
        try {
            authenticationService.register(email, password);
        } catch (RegistrationException e) {
            throw new RuntimeException("User already exists");
        }
        try {
            authenticationService.login(email, password);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Wrong password or email");
        }

        password = "12345";
        try {
            authenticationService.login(email, password);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Wrong password or email");
        }
    }
}
