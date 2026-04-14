package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authService =
                (AuthenticationService) injector
                        .getInstance(AuthenticationService.class);

        try {
            authService.register("test@gmail.com", "1234");
            System.out.println("User registered");

            authService.login("test@gmail.com", "1234");
            System.out.println("Login success");

        } catch (RegistrationException | AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
