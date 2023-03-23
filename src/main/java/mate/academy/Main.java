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

public class Main {
    private static Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

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

        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
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

        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));
        // Create Users
        final AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        User firstUser = new User();
        firstUser.setMail("Gmail.com");
        firstUser.setPassword("12345");

        User secondUser = new User();
        secondUser.setMail("Email.com");
        secondUser.setPassword("12345");

        User thirdUser = new User();
        thirdUser.setMail("Ad.com");
        thirdUser.setPassword("12345");

        User dublicateUser = new User();
        dublicateUser.setMail("Gmail.com");
        dublicateUser.setPassword("12345");

        //Checking the operation of registration and user login
        try {
            authenticationService.register(firstUser.getMail(), firstUser.getPassword());
            authenticationService.login(firstUser.getMail(),firstUser.getPassword());
            authenticationService.register(secondUser.getMail(), secondUser.getPassword());
            authenticationService.login(secondUser.getMail(),secondUser.getPassword());
        } catch (AuthenticationException | RegistrationException e) {
            System.out.println(e.getMessage());
        }
        //Try registration user with same mail
        try {
            authenticationService.register(dublicateUser.getMail(), dublicateUser.getPassword());
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }
        //Try login by not registered user
        try {
            authenticationService.login(thirdUser.getMail(), thirdUser.getPassword());
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }
}
