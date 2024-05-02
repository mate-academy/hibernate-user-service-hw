package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        User alice = new User("alice@i.ua");
        alice.setPassword("123");

        User alice2 = new User("alice@i.ua");
        alice2.setPassword("123");

        User bob = new User("bob@ukr.net");
        bob.setPassword("bob12345");

        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.add(alice);
        // userService.add(alice2);
        userService.add(bob);

        System.out.println("Result of - userService.findByEmail(alice.getEmail()):");
        System.out.println(userService.findByEmail(alice.getEmail()));

        System.out.println("Result of - userService.findByEmail(\"bob@ukr.net\"):");
        System.out.println(userService.findByEmail("bob@ukr.net"));

        System.out.println("Result of - userService.findByEmail(\"bob@i.ua\"):");
        System.out.println(userService.findByEmail("bob@i.ua"));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User loginedUser = authenticationService.login("alice@i.ua", "123");
        // User unLoginedUser = authenticationService.login("alice@i.ua", "12345");
        System.out.println("Result of - loginedUser with email alice@i.ua, and password 123:");
        System.out.println(loginedUser);
        // System.out.println(unLoginedUser);

        User registredUser = authenticationService.register("mike@google.com", "12345");
        System.out.println(
                "Result of - registredUser with email mike@google.com and password 12345:");
        System.out.println(registredUser);

        // User unRegisteredUser = authenticationService.register("alice@i.ua", "123");
        // System.out.println(unRegisteredUser);
    }
}
