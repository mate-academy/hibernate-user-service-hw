package mate.academy;

import mate.academy.authentication.AuthenticationService;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws AuthenticationException, RegistrationException {
        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authenticationService.register("avd998ne@gmail.com", "11223344");
        authenticationService.login("avd998ne@gmail.com", "11223344");
        authenticationService.register("avd998*#ne@gmail.com", "11223344");
    }
}
