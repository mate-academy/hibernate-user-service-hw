package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
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

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

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
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
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
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        String bobEmail = "bob@gmail.com";
        String aliceEmail = "alice@gmail.com";

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        System.out.println("Register: " + authenticationService
                .register(bobEmail, "qwerty"));
        System.out.println("Register: " + authenticationService
                .register(aliceEmail, "123456"));
        User bobFromDb = authenticationService.login(bobEmail, "qwerty");
        User aliceFromDb = authenticationService.login(aliceEmail, "123456");

        System.out.println("Email: " + bobFromDb.getEmail()
                + ". Password hash: " + bobFromDb.getPassword()
                + ". Salt: " + Arrays.toString(bobFromDb.getSalt()));
        System.out.println("Email: " + aliceFromDb.getEmail()
                + ". Password hash: " + aliceFromDb.getPassword()
                + ". Salt: " + Arrays.toString(aliceFromDb.getSalt()));

        // Wrong data
        //authenticationService.register("bob@gmail.com", "gssagasg");
        //authenticationService.register("test@gmail.com", "");
        //authenticationService.login("test@gmail.com", "123");
        //authenticationService.login("bob@gmail.com", "gssagasg");

    }
}
