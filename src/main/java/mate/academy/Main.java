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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final UserService userService = (UserService) injector
            .getInstance(UserService.class);
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

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

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User bobUser = new User();
        bobUser.setEmail("1234@gmail.com");
        bobUser.setPassword("1234");

        User bohdanUser = new User();
        bohdanUser.setEmail("bohdan@gmail.com");
        bohdanUser.setPassword("password");

        System.out.println(userService.add(bobUser));
        System.out.println(userService.add(bohdanUser));

        System.out.println(userService.findByEmail(bobUser.getEmail()));
        System.out.println(userService.findByEmail(bohdanUser.getEmail()));

        System.out.println(authenticationService
                .register(bobUser.getEmail(), bobUser.getPassword()));
        try {
            System.out.println(authenticationService
                    .login(bobUser.getEmail(), bobUser.getPassword()));
        } catch (AuthenticationException e) {
            System.out.println("Can't login");
        }
        try {
            System.out.println(authenticationService
                    .login(bobUser.getEmail(), "dfhgdf"));
        } catch (AuthenticationException e) {
            System.out.println("Wrong password");
        }
        try {
            System.out.println(authenticationService
                    .login("someshit", bobUser.getPassword()));
        } catch (AuthenticationException e) {
            System.out.println("Wrong email");
        }
        try {
            System.out.println(authenticationService
                    .login(bohdanUser.getEmail(), bohdanUser.getPassword()));
        } catch (AuthenticationException e) {
            System.out.println("Such user doesn't exist");
        }
        System.out.println(authenticationService
                .register(bobUser.getEmail(), bohdanUser.getPassword()));
    }
}
