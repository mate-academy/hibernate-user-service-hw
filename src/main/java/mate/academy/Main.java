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
    private static final String INJECTOR_INSTANCE = "mate.academy";
    private static final Injector injector = Injector.getInstance(INJECTOR_INSTANCE);

    private static final int PASSWORD_MIN_LENGTH = 8;

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
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

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
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

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));
        String goodUserEmail = "good@gmail.com";
        String goodUserPassword = "strongPassword";
        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User registeredGoodUser;
        if (goodUserEmail.contains("@") || goodUserPassword.length() >= PASSWORD_MIN_LENGTH) {
            registeredGoodUser = authenticationService
                    .register(goodUserEmail, goodUserPassword);
        } else {
            throw new RegistrationException("Can't register user with email " + goodUserEmail);
        }
        System.out.println(registeredGoodUser);
        String badUserEmail = "bad@gmail.com";
        String badUserPassword = "1q2w3e";
        String savedPasswordForGoodUser = userService.findByEmail(goodUserEmail)
                .get().getPassword();
        if (goodUserEmail.contains("@") && goodUserPassword.length() >= PASSWORD_MIN_LENGTH
                && savedPasswordForGoodUser.equals(goodUserPassword)) {
            authenticationService.login(goodUserEmail, goodUserPassword);
        } else {
            throw new AuthenticationException("Can't login user with email " + goodUserEmail
                    + "! May be, wrong email or password?");
        }
        String savedPasswordForBadUser = userService.findByEmail(badUserEmail).get().getPassword();
        if (badUserEmail.contains("@") && badUserPassword.length() >= PASSWORD_MIN_LENGTH
                && savedPasswordForGoodUser.equals(goodUserPassword)) {
            authenticationService.login(badUserEmail, badUserPassword);
        } else {
            throw new AuthenticationException("Can't login user with email " + goodUserEmail
                    + "! May be, wrong email or password?");
        }
        System.out.println(userService.findByEmail(badUserEmail).get());
    }
}
