package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.*;
import mate.academy.service.impl.AuthenticationServiceImpl;
import mate.academy.service.impl.UserServiceImpl;

public class Main {
    @Inject
    private static AuthenticationService authService = new AuthenticationServiceImpl();

    @Inject
    private static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws AuthenticationException, RegistrationException {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = null;

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

        CinemaHallService cinemaHallService = null;
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

        MovieSessionService movieSessionService = null;
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        try {
            // Register a new user
            authService.register("test@example.com", "password123");

            // Login with the registered user
            authService.login("test@example.com", "password123");
            System.out.println("Login successful.");

            // Retrieve the user using UserService
            userService.findByEmail("test@example.com").ifPresent(user ->
                    System.out.println("User retrieved: " + user.getEmail()));
        } catch (RegistrationException e) {
            throw new RegistrationException("Registration Error: " + e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Authentication Error: " + e.getMessage());
        }
    }
}
