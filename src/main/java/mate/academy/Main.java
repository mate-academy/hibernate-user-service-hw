package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        AuthenticationService authService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authService.register("mykhailo123", "ghawt?2");
        authService.login("mykhailo123", "ghawt?2");
    }
}
