package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        String email = "bob@asd.com";
        String password = "b1234567";
        try {
            authenticationService.register(email, password);
            System.out.println(authenticationService.login(email, password));
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register user with email: " + email, e);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login user with email: " + email, e);
        }
    }
}
