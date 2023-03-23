package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("123@gmail.com", "swordfish");
            authenticationService.register("zoom@i.ua", "drowssap");
        } catch (RegistrationException e) {
            System.out.println("exception!!!!");
        }
        try {
            System.out.println(authenticationService.login("zoom@i.ua", "drowssap").getId());
            System.out.println(authenticationService.login("zoom@i.ua", "password").getId());
            System.out.println(authenticationService.login("123@gmail.com", "password").getId());
            System.out.println(authenticationService.login("123@gmail.com", "swordfish").getId());
        } catch (AuthenticationException e) {
            System.out.println("!!!!!exception");
        }
        try {
            System.out.println(authenticationService.login("123@gmail.com", "swordfish").getId());
            System.out.println(authenticationService.login("123@gmail.com", "password").getId());
        } catch (AuthenticationException e) {
            System.out.println("EXEPTION");
        }
    }
}
