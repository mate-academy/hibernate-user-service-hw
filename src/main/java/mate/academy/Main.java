package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegisterException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    
    public static void main(String[] args) throws RegisterException, AuthenticationException {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        authenticationService.register("test", "pass");
        System.out.println(authenticationService.login("test", "pass"));
    }
}
