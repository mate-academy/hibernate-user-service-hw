package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                  = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        try {
            authenticationService.register("Abcd", "123");
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            System.out.println(authenticationService.login("Abcd", "123"));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            System.out.println(authenticationService.login("Abcd", "124"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
