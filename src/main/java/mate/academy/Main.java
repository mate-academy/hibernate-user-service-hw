package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("user1@gmail.com", "1234");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.register("user2@gmail.com", "5678");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("user1@gmail.com", "1234");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            // user exists but invalid password
            authenticationService.login("user2@gmail.com", "1111");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            // user doesnt exists
            authenticationService.login("user3@gmail.com", "2222");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
