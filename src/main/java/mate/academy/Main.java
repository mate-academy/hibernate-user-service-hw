package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authenticationService.register("test", "password");
    }
}
