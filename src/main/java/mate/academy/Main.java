package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("lavryniuk.dev@gmail.com", "123456789");
            authenticationService.register("young_trappa@gmail.com", "2281337");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register", e);
        }
        try {
            authenticationService.login("lavryniuk.dev@gmail.com", "123456789");
            authenticationService.login("young_trappa@gmail.com", "123");
        }  catch (AuthenticationException e) {
            throw new RuntimeException("Can't login", e);
        }
    }
}
