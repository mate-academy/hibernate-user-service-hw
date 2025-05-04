package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        User bob = new User();
        bob.setEmail("power@mail.com");
        bob.setPassword("1234");

        User alice = new User();
        alice.setEmail("alice_w@mail.com");
        alice.setPassword("qwerrty");

        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

        User registeredBob = authenticationService.register(bob.getEmail(), bob.getPassword());
        User registeredAlice = authenticationService
                .register(alice.getEmail(), alice.getPassword());
        System.out.println(registeredBob);
        System.out.println(registeredAlice);

        User logginedBob = authenticationService.login(bob.getEmail(), bob.getPassword());
        User logginedAlice = authenticationService.login(alice.getEmail(), alice.getPassword());
        System.out.println(logginedBob);
        System.out.println(logginedAlice);

    }
}
