package mate.academy;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        final AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        String email = "bob@asd.com";
        String password = "b1234567";
        authenticationService.register(email, password);
        System.out.println(authenticationService.login(email, password));
    }
}
