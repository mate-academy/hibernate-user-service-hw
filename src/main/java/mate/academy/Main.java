package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) throws AuthenticationException, RegistrationException {
        try {
            authenticationService.register("12345@gmail.com", "12345");
        } catch (RegistrationException e) {
            throw new RegistrationException("Can't register a user");
        }
        try {
            authenticationService.login("12345@gmail.com", "12345");
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Email or password is invalid");
        }
    }
}
