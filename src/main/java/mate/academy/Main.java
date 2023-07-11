package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) throws AuthenticationException, RegistrationException {
        User bobRegister = authenticationService.register("bob@gmail.com", "qwerty");
        User bobLogin = authenticationService.login("bob@gmail.com", "qwerty");
        System.out.println(bobRegister);
        System.out.println(bobLogin);
        User aliceRegister = authenticationService.register("alice@gmail.com", "ytrewq");
        User aliceLogin = authenticationService.login("alice@gmail.com", "ytrewq1");
        System.out.println(aliceRegister);
        System.out.println(aliceLogin);
    }
}
