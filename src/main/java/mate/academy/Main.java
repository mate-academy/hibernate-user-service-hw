package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("bob@mail.com", "bob_password");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            authenticationService.login("bob@mail.com", "bob_password");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
