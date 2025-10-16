package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {

        AuthenticationService authenticationServiceImpl =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        authenticationServiceImpl.register("example@domen.com", "exmpass1324");

        System.out.println(authenticationServiceImpl.login("example@domen.com", "exmpass1324"));
    }
}
