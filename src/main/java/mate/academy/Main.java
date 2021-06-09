package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.EmailAlreadyExistsException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("romaniukandrii7@gmail.com", "12345678");
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.register("bob@gmail.com", "9863413");
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.register("alica@gmail.com", "16543");
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login("romaniukandrii7@gmail.com", "12345678");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login("bob@gmail.com", "9863413");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login("alica@gmail.com", "16543");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
