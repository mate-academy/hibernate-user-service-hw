package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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

    private static final String MAIN_PACKAGE_NAME = "mate.academy";
    private static final Injector injector =
            Injector.getInstance(MAIN_PACKAGE_NAME);
    private static final String MOVIE_TITLE = "Fast and Furious";
    private static final String MOVIE_DESCRIPTION = "An action film about "
            + "street racing, heists, and spies.";
    private static final String FIRST_HALL_CAPACITY = "first hall with capacity 100";
    private static final String SECOND_HALL_CAPACITY = "second hall with capacity 200";
    private static final long ONE_DAY_OFFSET = 1L;
    private static final String USER_PASSWORD = "nazar1177";
    private static final String USER_EMAIL = "nazar@gmail.com";
    private static final String NOT_EXIST_USER_EMAIL = "petro@gmail.com";

    public static void main(String[] args) throws Exception {

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie(MOVIE_TITLE);
        fastAndFurious.setDescription(MOVIE_DESCRIPTION);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription(FIRST_HALL_CAPACITY);

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription(SECOND_HALL_CAPACITY);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(ONE_DAY_OFFSET));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(ONE_DAY_OFFSET));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = new User();
        user.setPassword(USER_PASSWORD);
        user.setEmail(USER_EMAIL);
        userService.add(user);
        System.out.println(user);

        Optional<User> byEmail = userService.findByEmail(USER_EMAIL);
        System.out.println(byEmail);

        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);

        User login = authenticationService.login(USER_EMAIL, USER_PASSWORD);
        System.out.println(login);

        User loginUser = authenticationService.register(NOT_EXIST_USER_EMAIL, USER_PASSWORD);
        System.out.println(loginUser);
    }
}
