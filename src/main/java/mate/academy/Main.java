package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService = (AuthenticationService) INJECTOR
                .getInstance(AuthenticationService.class);
        try {
            authenticationService.register("Email@gmail.com", "12345678");
            authenticationService.register("email1@gmail.com", "12345678");
            authenticationService.register("email2@gmail.com", "qwertyqwer");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            User login = authenticationService.login("email1@gmail.com", "12345678");
            System.out.println(login);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
