package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static UserService userService
            = (UserService) injector.getInstance(UserService.class);
    private static AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) throws AuthenticationException {
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

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User bob = new User();
        bob.setEmail("bob@gmail.com");
        bob.setPassword("qwerty");

        userService.add(bob);

        User bobLogin = authenticationService.login("bob@gmail.com", "qwerty");
        System.out.println(bobLogin);
        authenticationService.login("kdsafsd", "dsaklf");
        authenticationService.register("alice@gmail.com", "qwerty");
        User alice = authenticationService.login("alice@gmail.com", "qwerty");
        System.out.println(alice);
        authenticationService.register("alice@gmail.com", "qwerty");

    }
}
