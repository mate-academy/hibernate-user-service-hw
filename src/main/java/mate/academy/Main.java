package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {

        AuthenticationService instance = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        instance.register("user1@gmail.com", "1234");
        instance.register("user2@gmial.com", "12345");
        instance.register("user22@gmail.com", "qwerty");

        instance.login("user1@gmail.com", "1234");
        instance.login("user2@gmial.com", "12345");
        //instance.login("user22@gmail.com", "wrong");

        instance.register("user1@gmail.com", "1234");
    }
}
