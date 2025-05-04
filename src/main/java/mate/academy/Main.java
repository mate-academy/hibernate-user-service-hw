package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {

        User user = new User("user55425422@gmail.com", "qwerty12345");
        AuthenticationService authenticationService = (AuthenticationService)
                INJECTOR.getInstance((AuthenticationService.class));
        User register = authenticationService.register(user.getEmail(), user.getPassword());
        System.out.println("REGISTRATION");
        System.out.println(register);
        User login = authenticationService.login(user.getEmail(), user.getPassword());
        System.out.println("AUTHENTICATION");
        System.out.println(login);
    }
}
