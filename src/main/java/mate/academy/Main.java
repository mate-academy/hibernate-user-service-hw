package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
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
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final UserService userService
            = (UserService) injector.getInstance(UserService.class);

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

        User bob = new User("bob@gmail.com", "b0bsP@ssw0rd");
        userService.add(bob);

        System.out.println("____________________________________________________");
        System.out.println("Adding new user Alice");
        try {
            authenticationService.register("alice@gmail.com", "al1c3P@ssw0rd");
            System.out.println("Alice successfully registered");
        } catch (DataProcessingException e) {
            System.out.println("Can't register Alice");
        }

        System.out.println("____________________________________________________");
        System.out.println("Adding existing user Bob");
        try {
            authenticationService.register("bob@gmail.com", "b0bsP@ssw0rd");
            System.out.println("Bob successfully registered");
        } catch (DataProcessingException e) {
            System.out.println("Can't register Bob");
        }

        System.out.println("____________________________________________________");
        System.out.println("Trying login with wright authentication data");
        try {
            authenticationService.login("alice@gmail.com", "al1c3P@ssw0rd");
            System.out.println("Alice successfully logged");
        } catch (AuthenticationException e) {
            System.out.println("Can't login Alice with wright authentication data");
        }

        System.out.println("____________________________________________________");
        System.out.println("Trying login with wrong email");
        try {
            authenticationService.login("alicewrong@gmail.com", "al1c3P@ssw0rd");
            System.out.println("Alice successfully logged");
        } catch (AuthenticationException e) {
            System.out.println("Can't login Alice with wrong email");
        }

        System.out.println("____________________________________________________");
        System.out.println("Trying login with wrong password");
        try {
            authenticationService.login("alice@gmail.com", "al1c3P@ssw0rdWrong");
            System.out.println("Alice successfully logged");
        } catch (AuthenticationException e) {
            System.out.println("Can't login Alice with wrong password");
        }
    }
}
