package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);
        System.out.println(authenticationService.register("some email", "12345678"));
        System.out.println(authenticationService.register("hello world", "hello mates"));
        System.out.println(authenticationService.login("hello world", "hello mates"));
    }
}
