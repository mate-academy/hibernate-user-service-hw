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
            System.out.println(authenticationService.login("lavryniuk.dev@gmail.com", "123456789"));
        } catch (RegistrationException | AuthenticationException e) {
            throw new RuntimeException("Can't register or login");
        }
    }
}
