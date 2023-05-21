package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("login", "123456");
        authenticationService.register("email", "qwerty");
        authenticationService.register("something", "hello");
        authenticationService.login("something", "hello");
    }
}
