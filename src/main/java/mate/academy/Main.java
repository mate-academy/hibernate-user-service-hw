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

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

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

        System.out.println("111111" + movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println("222222" + movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User firstUser = new User();
        firstUser.setEmail("first@gmail.com");
        firstUser.setPassword("firstPass");

        User secondUser = new User();
        secondUser.setEmail("second@gmail.com");
        secondUser.setPassword("secondPass");

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            System.out.println(authenticationService.register(firstUser.getEmail(),
                    firstUser.getPassword()));
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register the user: "
                    + firstUser, e);
        }
        try {
            System.out.println(authenticationService.register(secondUser.getEmail(),
                    secondUser.getPassword()));
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register the user: "
                    + secondUser, e);
        }
        try {
            System.out.println(authenticationService.login(firstUser.getEmail(),
                    firstUser.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login using email: "
                    + firstUser.getEmail(), e);
        }
        try {
            System.out.println(authenticationService.login(secondUser.getEmail(),
                    secondUser.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login using emails: "
                    + secondUser.getEmail(), e);
        }
    }
}
