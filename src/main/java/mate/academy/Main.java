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

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

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

        // register
        System.out.println("--------- register ------------");
        AuthenticationService authenticationService = (AuthenticationService)
                        injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.register("alice@gmail.com", "qwerty");
            authenticationService.register("bob@gmail.com", "123456");
            authenticationService.register("notValid_email", "111111");

        } catch (RegistrationException e) {
            System.out.println("Can't register user: not valid Email or user already exist");
        }
        try {
            authenticationService.register("alice@gmail.com", "qwerty");
        } catch (RegistrationException e) {
            System.out.println("Can't register user: entered email already exist");
        }


        // login
        System.out.println("--------- login ------------");
        try {
            System.out.println(authenticationService.login("alice@gmail.com", "qwerqqty"));
        } catch (AuthenticationException e) {
            System.out.println("Can't login with this email and password");
        }
        try {
            System.out.println(authenticationService.login("bob@gmail.com", "123456"));
        } catch (AuthenticationException e) {
            System.out.println("Can't login with this email and password");
        }
        try {
            System.out.println(authenticationService.login("ali@gmail.com", "qwerty"));
        } catch (AuthenticationException e) {
            System.out.println("Can't login with this email and password");
        }
    }
}
