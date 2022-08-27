package mate.academy;

//import java.time.LocalDate;
//import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
//import mate.academy.model.CinemaHall;
//import mate.academy.model.Movie;
//import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
//import mate.academy.service.CinemaHallService;
//import mate.academy.service.MovieService;
//import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        /*
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
        */

        { // AuthenticationService testing block
            Injector injector = Injector.getInstance("mate.academy");
            AuthenticationService authenticationService
                    = (AuthenticationService) injector.getInstance(AuthenticationService.class);

            try {
                authenticationService.register("user@server.domain", "1234");
            } catch (RegistrationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Successful registration");
            }

            try {
                authenticationService.register("user@server.domain", "1234");
            } catch (RegistrationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful registration, repeated email");
            }

            try {
                authenticationService.register(null, "1234");
            } catch (RegistrationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful registration, email == null");
            }

            try {
                authenticationService.register("user@server.domain", null);
            } catch (RegistrationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful registration, password == null");
            }

            User loggedUser = null;
            try {
                loggedUser = authenticationService.login("user@server.domain", "1234");
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Successful login");
            }
            System.out.println(loggedUser);

            loggedUser = null;
            try {
                loggedUser = authenticationService.login("user@server.domain", "12345");
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful login, wrong password");
            }
            System.out.println(loggedUser);

            loggedUser = null;
            try {
                loggedUser = authenticationService.login(null, "12345");
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful login, email == null");
            }
            System.out.println(loggedUser);

            loggedUser = null;
            try {
                loggedUser = authenticationService.login("user@server.domain", null);
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful login, password == null");
            }
            System.out.println(loggedUser);

            loggedUser = null;
            try {
                loggedUser = authenticationService.login("user@server.domain", "");
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Unsuccessful login, password is blank");
            }
            System.out.println(loggedUser);
        }
    }
}
