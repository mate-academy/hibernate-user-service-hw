package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
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
    private static final String SEPARATOR = System.lineSeparator();
    private static AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static UserService userService
            = (UserService) injector.getInstance(UserService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie fastAndFuriousTwo = new Movie("Fast and Furious 2");
        fastAndFuriousTwo.setDescription(
                "Second part of an action film about street racing, heists, and spies.");
        Movie fastAndFuriousThree = new Movie("Fast and Furious 3");
        fastAndFuriousThree.setDescription(
                "Third part of an action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.add(fastAndFuriousTwo);
        movieService.add(fastAndFuriousThree);
        System.out.println(movieService.get(fastAndFurious.getId())
                + SEPARATOR + "List of all movies: ");
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("First hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("Second hall with capacity 200");

        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.get(firstCinemaHall.getId())
                + SEPARATOR + "List of all cinemas: ");
        System.out.println(cinemaHallService.getAll());

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println("Find yesterday movie session: "
                + movieSessionService.get(yesterdayMovieSession.getId()) + SEPARATOR);
        System.out.println("Find available movie sessions: "
                + movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));

        User bob = new User("bob@gmail.com", "bob");
        User alice = new User("alice@gmail.com", "alice");

        userService.add(bob);
        userService.add(alice);
        System.out.println(userService.findByEmail("alice@gmail.com"));

        authenticationService.register("frederik@gmail.com", "frederik");
        try {
            authenticationService.register("frederik@gmail.com", "fred");
        } catch (RuntimeException e) {
            System.out.println("Can`t save user. Email is already exist.");
        }

        try {
            System.out.println(authenticationService.login("bob@gmail.com", "bob"));
            System.out.println(authenticationService.login("bob@gmail.com", "BOB"));
        } catch (AuthenticationException e) {
            System.out.println("Wrong email or password!");
        }
    }
}
