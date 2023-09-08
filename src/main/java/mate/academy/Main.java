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
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final MovieService MOVIE_SERVICE = (MovieService) INJECTOR
            .getInstance(MovieService.class);
    private static final CinemaHallService CINEMA_HALL_SERVICE = (CinemaHallService) INJECTOR
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService MOVIE_SESSION_SERVICE = (MovieSessionService) INJECTOR
            .getInstance(MovieSessionService.class);
    private static final UserService USER_SERVICE = (UserService) INJECTOR
            .getInstance(UserService.class);
    private static final AuthenticationService AUTHENTICATION_SERVICE =
            (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MOVIE_SERVICE.add(fastAndFurious);
        System.out.println(MOVIE_SERVICE.get(fastAndFurious.getId()));
        MOVIE_SERVICE.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CINEMA_HALL_SERVICE.add(firstCinemaHall);
        CINEMA_HALL_SERVICE.add(secondCinemaHall);

        System.out.println(CINEMA_HALL_SERVICE.getAll());
        System.out.println(CINEMA_HALL_SERVICE.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MOVIE_SESSION_SERVICE.add(tomorrowMovieSession);
        MOVIE_SESSION_SERVICE.add(yesterdayMovieSession);

        System.out.println(MOVIE_SESSION_SERVICE.get(yesterdayMovieSession.getId()));
        System.out.println(MOVIE_SESSION_SERVICE.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User alice = new User();
        alice.setEmail("alice@gmail.com");
        alice.setPassword("qwerty");
        alice.setSalt(HashUtil.getSalt());
        USER_SERVICE.add(alice);
        User bob = new User();
        bob.setEmail("bob@gmail.com");
        bob.setPassword("q1w2e3r4t5");
        bob.setSalt(HashUtil.getSalt());
        USER_SERVICE.add(bob);
        System.out.println(USER_SERVICE.findByEmail(alice.getEmail()));
        try {
            AUTHENTICATION_SERVICE.register("bill@gmail.com", "12345");
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(AUTHENTICATION_SERVICE.login("bill@gmail.com", "12345"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

    }
}
