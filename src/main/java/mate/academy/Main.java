package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector INJECTOR_MAP = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) INJECTOR_MAP.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR_MAP.getInstance(CinemaHallService.class);

        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR_MAP.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        UserService userService =
                (UserService) INJECTOR_MAP.getInstance(UserService.class);
        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR_MAP.getInstance(AuthenticationService.class);

        try {
            authenticationService.register("test1@email.com", "querty1");
        } catch (RegistrationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.register("test2@email.com", "querty2");
        } catch (RegistrationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.register("test2@email.com", "querty3");
        } catch (RegistrationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.login("test@email.com", "querty");
        } catch (AuthenticationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.login("test1@email.com", "querty");
        } catch (AuthenticationException e) {
            System.out.println(e);
        }

        try {
            authenticationService.login("", "querty");
        } catch (AuthenticationException e) {
            System.out.println(e);
        }
    }
}
