package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("americanakita2022@gmail.com", "12345678q");
        authenticationService.login("americanakita2022@gmail.com", "12345678q");
    }
}
