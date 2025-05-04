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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

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

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        String email = "test@example.com";
        String password = "securePassword";

        // Scenario 1: Register a new user
        try {
            User registeredUser = authenticationService.register(email, password);
            System.out.println("User registered: " + registeredUser);
        } catch (RegistrationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        // Scenario 2: Attempt to register with an already taken email
        try {
            User registeredUserAgain = authenticationService.register(email, password);
            System.out.println("User registered again: " + registeredUserAgain);
        } catch (RegistrationException e) {
            System.out.println("Registration failed with existing email: " + e.getMessage());
        }

        // Scenario 3: Login with correct email and password
        try {
            User loggedInUser = authenticationService.login(email, password);
            System.out.println("User logged in: " + loggedInUser);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }

        // Scenario 4: Login with incorrect password
        String wrongPassword = "wrongPassword";
        try {
            User loggedInUser = authenticationService.login(email, wrongPassword);
            System.out.println("User logged in: " + loggedInUser);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed with wrong password: " + e.getMessage());
        }

        // Scenario 5: Login with incorrect email
        String wrongEmail = "wrong@example.com";
        try {
            User loggedInUser = authenticationService.login(wrongEmail, password);
            System.out.println("User logged in: " + loggedInUser);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed with wrong email: " + e.getMessage());
        }
    }
}
