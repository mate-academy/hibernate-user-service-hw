package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User ben = new User("BenyHill@gmail.com", "991");
        User melinda = new User("MelindaGates@gmail.com", "win");
        User melinda2 = new User("MelindaGates@gmail.com", "win");
        try {
            authenticationService.register(ben.getEmail(), ben.getPassword());
            System.out.println("Registration successfully completed");
        } catch (RegistrationException e) {
            System.out.println("Registration error");
        }
        try {
            authenticationService.register(melinda.getEmail(), melinda.getPassword());
            System.out.println("Registration successfully completed");
        } catch (RegistrationException e) {
            System.out.println("Registration error");
        }
        try {
            authenticationService.register(melinda2.getEmail(), melinda2.getPassword());
            System.out.println("Registration successfully completed");
        } catch (RegistrationException e) {
            System.out.println("Registration error. Email already exist");
        }
        try {
            authenticationService.login(ben.getEmail(), ben.getPassword());
            System.out.println("You successfully enter into system");
        } catch (AuthenticationException e) {
            System.out.println("Can't authorise user. Wrong password or email address");
        }
        try {
            authenticationService.login(melinda.getEmail(), melinda.getPassword());
            System.out.println("You successfully enter into system");
        } catch (AuthenticationException e) {
            System.out.println("Can't authorise user. Wrong password or email address");
        }
        try {
            authenticationService.login(melinda.getEmail(), "I forgot");
            System.out.println("You successfully enter into system");
        } catch (AuthenticationException e) {
            System.out.println("Can't authorise user. Wrong password or email address");
        }
    }
}
