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
            authenticationService.register("user1@gmail.com", ""); // bad pass
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            authenticationService.register("user1@gmail.com", "1111");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            authenticationService.register("user2@gmail.com", "2222");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("user2@gmail.com", "2222"); // all good
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("user1@gmail.com", "1234"); // bad pass
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        try {
            authenticationService.login("user5@gmail.com", "2222"); // bad mail
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
