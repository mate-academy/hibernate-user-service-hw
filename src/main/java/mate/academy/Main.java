package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        try {
            authenticationService.register("danylo.kozakov@gmail.ua", "1814");
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register user ", e);
        }
        User loginUser;
        try {
            loginUser = authenticationService.login("danylo.kozakov@gmail.ua", "1814");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login user ", e);
        }
        System.out.println(loginUser);
    }
}
