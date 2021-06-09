package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        System.out.println(authenticationService.register("BenyHill@gmail.com", "991"));
        System.out.println(authenticationService.register("MelindaGates@gmail.com", "win"));
        System.out.println(authenticationService.register("MelindaGates@gmail.com", "win_1"));
    }
}
