package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User maxine = new User();
        maxine.setEmail("maxine.qwer@gmail.com");
        maxine.setPassword("QWERTY12");
        try {
            authenticationService.register(maxine.getEmail(), maxine.getPassword());
            System.out.println("User with email=" + maxine.getEmail() + " was registered");
        } catch (RegistrationException e) {
            System.out.println("RegistrationException cached");
        }

        try {
            authenticationService.login(maxine.getEmail(), maxine.getPassword());
            System.out.println("User with email=" + maxine.getEmail() + " was logginned");
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException cached");
        }

        User anotherMaxine = new User();
        anotherMaxine.setEmail("maxine.qwer@gmail.com");
        anotherMaxine.setPassword("YIRT");

        try {
            authenticationService.register(anotherMaxine.getEmail(), anotherMaxine.getPassword());
            System.out.println("User with email=" + anotherMaxine.getEmail() + " was registered");
        } catch (RegistrationException e) {
            System.out.println("RegistrationException cached");
        }

        try {
            authenticationService.login(anotherMaxine.getEmail(), anotherMaxine.getPassword());
            System.out.println("User with email=" + anotherMaxine.getEmail() + " was logginned");
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException cached");
        }

    }
}
