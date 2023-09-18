package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        try {
            authenticationService.register("test", "password");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login("test", "password");
            System.out.println("Success!");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login("test", "wrong");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
