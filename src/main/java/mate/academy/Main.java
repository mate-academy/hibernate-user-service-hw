package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        try {
            User user1 = authenticationService.register("elon.musk@spacex.com", "MyBigRocket");
            User user2 = authenticationService.register("santa.claus@northpole.nd", "HoHoHo");
        } catch (RegistrationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.register("elon.musk@spacex.com", "MyBigRocket");
        } catch (RegistrationException e) {
            System.out.println(e);
        }
        try {
            authenticationService.login("elon.musk@spacex.com", "MyBigRocker");
        } catch (AuthenticationException e) {
            System.out.println(e);
        }
        try {
            User loggedUser = authenticationService.login("elon.musk@spacex.com", "MyBigRocket");
            System.out.println(loggedUser);
        } catch (AuthenticationException e) {
            System.out.println(e);
        }
    }
}
