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

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService)
                INJECTOR.getInstance(MovieService.class);

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
                INJECTOR.getInstance(CinemaHallService.class);
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
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        User bob = new User("bob_jeferson@gmail.com", "qwerty");
        User alice = new User("alice_jackson@gmail.com","abc");

        User daniel = new User("danial@gmail.com", "qwerty");
        User john = new User("john_deep@gmail.com","");
        User kate = new User("alice_jackson@gmail.com", "123456");

        UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
        userService.add(bob);

        AuthenticationService authenticationService = (AuthenticationService)
                INJECTOR.getInstance(AuthenticationService.class);
        // Checks with valid data
        try {
            authenticationService.register(alice.getEmail(), alice.getPassword());
            alice = authenticationService.login(alice.getEmail(), alice.getPassword());
            authenticationService.register(daniel.getEmail(), daniel.getPassword());
            daniel = authenticationService.login(daniel.getEmail(), daniel.getPassword());
        } catch (RegistrationException | AuthenticationException e) {
            throw new RuntimeException("Can't register current user");
        }
        // Checks with invalid data
        try {
            authenticationService.register(john.getEmail(), john.getPassword());
            authenticationService.register(kate.getEmail(), kate.getPassword());
        } catch (RegistrationException e) {
            throw new RuntimeException("Can't register current user");
        }
        try {
            authenticationService.login(bob.getEmail(), alice.getPassword());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't login current user");
        }
    }
}
