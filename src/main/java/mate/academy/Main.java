package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService hallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService sessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        Movie dune = new Movie("Dune");
        dune.setDescription("Spice must flow...");
        movieService.add(dune);
        Movie highlander = new Movie("Highlander");
        highlander.setDescription("In the end, there can be only one...");
        movieService.add(highlander);
        Movie matrix = new Movie("Matrix");
        matrix.setDescription("Follow the white rabbit...");
        movieService.add(matrix);

        System.out.println(movieService.get(dune.getId()));
        System.out.println(movieService.get(matrix.getId()));
        System.out.println(movieService.getAll());

        CinemaHall smallHall = new CinemaHall(200, "Small hall");
        hallService.add(smallHall);
        CinemaHall middleHall = new CinemaHall(300, "Middle hall");
        hallService.add(middleHall);
        CinemaHall largeHall = new CinemaHall(500, "Large hall");
        hallService.add(largeHall);

        System.out.println(hallService.get(smallHall.getId()));
        System.out.println(hallService.get(middleHall.getId()));
        System.out.println(hallService.getAll());

        MovieSession sessionOne = new MovieSession(matrix, smallHall, LocalDateTime.now());
        sessionService.add(sessionOne);
        MovieSession sessionTwo = new MovieSession(dune, largeHall, LocalDateTime.now());
        sessionService.add(sessionTwo);
        MovieSession sessionThree =
                new MovieSession(highlander, middleHall, LocalDateTime.now().plusDays(2));
        sessionService.add(sessionThree);
        MovieSession sessionFour =
                new MovieSession(matrix, largeHall, LocalDateTime.now().plusDays(1));
        sessionService.add(sessionFour);
        MovieSession sessionFive =
                new MovieSession(highlander, largeHall, LocalDateTime.now().plusDays(2));
        sessionService.add(sessionFive);

        System.out.println(sessionService.get(sessionOne.getId()));
        System.out.println(sessionService.get(sessionThree.getId()));
        System.out.println(sessionService.get(sessionFive.getId()));

        System.out.println(sessionService.findAvailableSessions(matrix.getId(), LocalDate.now()));
        System.out.println(sessionService.findAvailableSessions(highlander.getId(),
                LocalDate.now().plusDays(2)));
        System.out.println(sessionService.findAvailableSessions(dune.getId(),
                LocalDate.now().minusDays(2)));

        authenticationService.register("bob@gmail.com", "hardPassword");
        System.out.println(authenticationService.login("bob@gmail.com", "hardPassword"));
        authenticationService.register("jack@gmail.com", "Password");
        System.out.println(authenticationService.login("jack@gmail.com", "Password"));

        authenticationService.register("bob@gmail.com", "hardPassword");
        authenticationService.login("wrongLogin", "wrongPassword");
    }
}
