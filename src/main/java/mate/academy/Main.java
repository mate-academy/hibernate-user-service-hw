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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

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

        User bob = new User();
        bob.setLogin("123@123");
        bob.setPassword("qwerty");

        AuthenticationService auth
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            User register = auth.register(bob.getLogin(), bob.getPassword());
            System.out.println(register);
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }
        try {
            User login = auth.login(bob.getLogin(), bob.getPassword());
            System.out.println(login);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        User sameEmailNotOk = new User();
        sameEmailNotOk.setLogin("123@123");
        sameEmailNotOk.setPassword("123");
        try {
            auth.register(sameEmailNotOk.getLogin(), sameEmailNotOk.getPassword());
        } catch (RegistrationException e) {
            System.out.println(e);
        }
        User loginDoesNotExistNotOk = new User();
        loginDoesNotExistNotOk.setLogin("qwe@qwe");
        loginDoesNotExistNotOk.setPassword("qwe");
        try {
            auth.login(loginDoesNotExistNotOk.getLogin(),
                    loginDoesNotExistNotOk.getPassword());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
