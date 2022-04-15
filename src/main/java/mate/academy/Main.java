package mate.academy;

import mate.academy.exception.AlreadyExistingEmailException;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService = (AuthenticationService)
            injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("mate@gmail.com", "12345");
        } catch (AlreadyExistingEmailException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.register("mate1@gmail.com", "11111");
        } catch (AlreadyExistingEmailException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("mate2@gmail.com", "22222");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }

        try {
            authenticationService.login("mate@gmail.com", "33333");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
