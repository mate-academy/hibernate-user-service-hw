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
import mate.academy.security.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.util.HashUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie retrievedMovie = movieService.get(fastAndFurious.getId());
        if (retrievedMovie != null) {
            System.out.println(retrievedMovie);
        } else {
            System.out.println("Movie not found in the database.");
        }
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
        CinemaHall retrievedCinemaHall = cinemaHallService.get(firstCinemaHall.getId());
        if (retrievedCinemaHall != null) {
            System.out.println(retrievedCinemaHall);
        } else {
            System.out.println("Cinema hall not found in the database.");
        }

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

        MovieSession retrievedMovieSession = movieSessionService.get(yesterdayMovieSession.getId());
        if (retrievedMovieSession != null) {
            System.out.println(retrievedMovieSession);
        } else {
            System.out.println("Movie session not found in the database.");
        }

        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User user = new User();
        user.setEmail("LikeMyWorld@gmail.com");

        String rawPassword = "123QWE";
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(rawPassword, salt);
        user.setPassword(hashedPassword);

        try {
            authenticationService.register(user.getEmail(), rawPassword);
            System.out.println("Registration successful for email: " + user.getEmail());
        } catch (RegistrationException e) {
            System.err.println("Registration failed: " + e.getMessage());
        }

        try {
            User loggedInUser = authenticationService.login(user.getEmail(), rawPassword);
            System.out.println("Login successful for email: " + loggedInUser.getEmail());
        } catch (AuthenticationException e) {
            System.err.println("Login failed: " + e.getMessage());
        }

        System.out.println("User email: " + user.getEmail());
    }
}
