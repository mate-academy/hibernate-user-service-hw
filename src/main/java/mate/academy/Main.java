package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

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

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
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

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("entersandman@mateacademy.br", "newpassNew");
            User loggedIn =
                    authenticationService.login("entersandman@mateacademy.br", "newpassNew");

            logger.info(loggedIn + " successfully logged in");
            logger.info("attempting to register with an existing email");
            authenticationService.register("entersandman@mateacademy.br", "New2newpass");
            logger.info("attempting register with an empty password");
            authenticationService.register("KirillKoval", "");
        } catch (RegistrationException e) {
            System.out.println("Registration exception" + e);
        } catch (AuthenticationException e) {
            System.out.println("Auth exception" + e);
        }

        try {
            logger.info("attempting to register with an empty email");
            authenticationService.register("", "hos@mate-academy.br");
        } catch (RegistrationException e) {
            System.out.println("Registration exception" + e);
        }

        try {
            logger.info("attempting to log in to an existing account with a wrong pass (0_0)");
            authenticationService.login("entersandman@mateacademy.br", "wrong");
        } catch (AuthenticationException e) {
            System.out.println("Auth exception" + e);
        }

        try {
            logger.info("attempting to log in to an non-existing account :-0");
            authenticationService.login("ente////rsa/n/dman@mat/e/", "correkt passw");
        } catch (AuthenticationException e) {
            System.out.println("Auth exception" + e);
        }
    }
}

