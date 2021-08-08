package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        authenticationService.register("12345@gmail.com", "12345");
        try {
            authenticationService.login("12345@gmail.com", "12345");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email or password is invalid");
        }
    }
}
