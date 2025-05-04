package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("danylo@gmail.com", "qwerty");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("danylo@gmail.com", "qwerty");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.register("danylo@gmail.com", "qw13erty");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
