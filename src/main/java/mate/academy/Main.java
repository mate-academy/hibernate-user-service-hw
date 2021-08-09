package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User bob = new User();
        bob.setName("Bob");
        bob = authenticationService.register("bob@mail.com", "bob123");

        System.out.println(userService.findByEmail(bob.getEmail()).get()
                + " found by email " + bob.getEmail());
        try {
            userService.findByEmail("mail@mail.com");
        } catch (Exception e) {
            System.out.println("Exception: can't find user by email not present in db - OK");
        }

        User alice = authenticationService.register("alice@mail.com", "123");
        try {
            alice = authenticationService.login(alice.getEmail(), "123");
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                    + alice.getEmail() + " - NOT OK");
        }

        try {
            authenticationService.login(bob.getEmail(), "565");
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                     + bob.getEmail() + " - OK");
        }

        try {
            bob = authenticationService.register(bob.getEmail(), bob.getPassword());
        } catch (Exception e) {
            System.out.println("Can't register user with: " + bob.getEmail() + " - OK");
        }
        try {
            authenticationService.login(bob.getEmail(), "wrongPassword");
        } catch (AuthenticationException e) {
            System.out.println("Can't login with "
                    + bob.getEmail() + " and wrong password, after registration - OK");
        }
    }
}
