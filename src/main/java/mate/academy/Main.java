package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("alexander.pushkin@mail.com", "natalia");
            System.out.println(authenticationService.login(
                    "alexander.pushkin@mail.com", "natalia"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
