package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User registerUser = authenticationService.register("oleg", "2604");

        System.out.println(registerUser);

        User loginUser = authenticationService.login("oleg", "2604");

        System.out.println(loginUser);

        System.out.println("registerUser == loginUser ? " + registerUser.equals(loginUser));
    }
}
