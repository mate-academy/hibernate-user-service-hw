package mate.academy;

import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
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

        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        try {
            authenticationService.register("bobmarley@gmail.com", "123321");
            logger.debug("Method register was called. Params: email = {}, password = {}",
                    "bobmarley@gmail.com", "123321");
        } catch (AuthenticationException e) {
            logger.error("This email exists in the database");
        }

        try {
            authenticationService.login("bobmarley@gmail.com", "123321");
            logger.debug("Method login was called. Params: email = {}, password = {}",
                    "bobmarley@gmail.com", "123321");
        } catch (AuthenticationException e) {
            logger.error("This email or password is uncorrected");
        }

        try {
            authenticationService.login("marleybob@gmail.com", "123321");
            logger.debug("Method login was called. Params: email = {}, password = {}",
                    "bobmarley@gmail.com", "123321");
        } catch (AuthenticationException e) {
            logger.error("This email or password is uncorrected");
        }

        try {
            authenticationService.login("bobmarley@gmail.com", "489651");
            logger.debug("Method login was called. Params: email = {}, password = {}",
                    "bobmarley@gmail.com", "123321");
        } catch (AuthenticationException e) {
            logger.error("This email or password is uncorrected");
        }

        try {
            authenticationService.register("bobmarley@gmail.com", "sdghfsdg");
        } catch (AuthenticationException e) {
            logger.error("This email exists in the database");
        }
    }
}
