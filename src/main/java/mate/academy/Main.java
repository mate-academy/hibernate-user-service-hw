package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        authenticationService.register("email1","password1");
        authenticationService.register("email2","password2");
        authenticationService.register("email3","password3");
        authenticationService.register("email4","password4");
        authenticationService.login("email4","password4");

    }
}
